package br.com.south.batch.sales.converter;

import br.com.south.batch.sales.annotation.PositionalLineIdentifier;
import br.com.south.batch.sales.annotation.PositionalField;
import br.com.south.batch.sales.exceptions.FieldValueDeserializerException;
import br.com.south.batch.sales.exceptions.PositionalDeserializerException;
import br.com.south.batch.sales.factory.FieldValueDeserializerGeneratorFactory;
import br.com.south.batch.sales.strategy.deserializer.FieldValueDeserializerGenerator;
import br.com.south.batch.sales.utils.file.FileHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_UTILITY_CLASS;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

class PositionalFileDeserializer {

    private PositionalFileDeserializer() {
        throw new IllegalArgumentException(getMessage(ERROR_UTILITY_CLASS));
    }

    static <T> T apply(final String base64, Class<T> clazz) throws PositionalDeserializerException {
        try {
            final File tmpFile = FileHelper.createTmpTextFileFromBase64(base64);
            return PositionalFileDeserializer.apply(tmpFile, clazz);
        } catch (Exception e) {
            throw new PositionalDeserializerException(e.getMessage(), e);
        }
    }

    static <T> T apply(File file, Class<T> clazz) throws PositionalDeserializerException {
        try {
            T aClazz = clazz.newInstance();

            for (Field embeddedField : Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(PositionalLineIdentifier.class))
                    .collect(Collectors.toList())) {

                embeddedField.setAccessible(true);

                if (Collection.class.isAssignableFrom(embeddedField.getType())) {
                    applyCollection(file, aClazz, embeddedField);
                } else {
                    applyObject(file, aClazz, embeddedField);
                }
            }

            return aClazz;
        } catch (FieldValueDeserializerException | InstantiationException | IllegalAccessException | IOException e) {
            throw new PositionalDeserializerException(e.getMessage(), e);
        }
    }

    static <T> void applyCollection(File file, T aClazz, Field field)
            throws IOException, InstantiationException, IllegalAccessException, FieldValueDeserializerException {

        final Type type = field.getGenericType();

        final ArrayList<Object> objects = new ArrayList<>();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;

            for (String line : Files.readAllLines(file.toPath())
                    .stream()
                    .filter(getLineFilterPredicate(field))
                    .collect(Collectors.toList())) {

                Object object = ((Class) pType.getActualTypeArguments()[0]).newInstance();
                String delimiter = field.getAnnotation(PositionalLineIdentifier.class).delimiter();
                applyFieldValue(line, object, delimiter);
                objects.add(object);
            }
        }

        field.set(aClazz, objects);
    }

    static <T> void applyObject(File file, T aClazz, Field field)
            throws IOException, InstantiationException, IllegalAccessException, FieldValueDeserializerException {

        Object object = field.getType().newInstance();

        String line = Files.readAllLines(file.toPath())
                .stream()
                .filter(getLineFilterPredicate(field))
                .findFirst()
                .orElse(StringUtils.EMPTY);

        String delimiter = field.getAnnotation(PositionalLineIdentifier.class).delimiter();

        applyFieldValue(line, object, delimiter);
        field.set(aClazz, object);
    }

    static void applyFieldValue(String line, Object object, String delimiter)
            throws IllegalAccessException, FieldValueDeserializerException, InstantiationException {

        for (Field field : Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(PositionalField.class))
                .collect(Collectors.toList())) {

            PositionalField.PositionalFileFormat type = field.getAnnotation(PositionalField.class).type();
            FieldValueDeserializerGenerator generator = FieldValueDeserializerGeneratorFactory.create(field);
            Object objectGenerated = generator.generate(field, line, delimiter);

            if(type.equals(PositionalField.PositionalFileFormat.LIST)){
                String listItemDelimiter = field.getAnnotation(PositionalField.class).listItemDelimiter();
                applyListValue((List<String>) objectGenerated, object, field, listItemDelimiter);
            }
            else{
                field.setAccessible(true);
                field.set(object, objectGenerated);
            }
        }
    }

    static void applyListValue(List<String> items, Object object, Field field, String listDelimiter) throws IllegalAccessException, InstantiationException, FieldValueDeserializerException {
        Type fieldType = field.getGenericType();
        ArrayList<Object> objectsList = new ArrayList<>();

        if (fieldType instanceof ParameterizedType) {
            ParameterizedType pFieldType = (ParameterizedType) fieldType;

            for (String itemString : items) {

                Object objectItem = ((Class) pFieldType.getActualTypeArguments()[0]).newInstance();
                applyFieldValue(itemString, objectItem, listDelimiter);
                objectsList.add(objectItem);
            }
        }
        field.setAccessible(true);
        field.set(object, objectsList);
    }

    static Predicate<String> getLineFilterPredicate(Field field) {
        return lines -> lines.startsWith(field.getAnnotation(PositionalLineIdentifier.class)
                .linesStartingWithChar());
    }

}
