package br.com.south.batch.sales.strategy.deserializer;
import br.com.south.batch.sales.annotation.PositionalField;
import br.com.south.batch.sales.exceptions.FieldValueDeserializerException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_DESERIALIZER_CONVERTER;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

public class FieldListValueDeserializer implements FieldValueDeserializerGenerator {

    public List<String> generate(Field field, String line, String delimiter) throws FieldValueDeserializerException {
        PositionalField positionalFieldAnn = field.getAnnotation(PositionalField.class);

        try {
            String stringExtracted = line.split(delimiter)[positionalFieldAnn.position() - 1];
            String stringFormatted = StringUtils.substringBetween(stringExtracted, positionalFieldAnn.listStart(), positionalFieldAnn.listEnd());
            return Arrays.asList(stringFormatted.split(positionalFieldAnn.listDelimiter()));
        } catch (Exception e) {
            throw new FieldValueDeserializerException(getMessage(ERROR_DESERIALIZER_CONVERTER,
                field.getName(), field.getDeclaringClass(), e.getMessage()), e);
        }
    }
}
