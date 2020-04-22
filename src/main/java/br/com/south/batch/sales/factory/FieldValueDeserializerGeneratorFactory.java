package br.com.south.batch.sales.factory;

import br.com.south.batch.sales.annotation.PositionalField;
import br.com.south.batch.sales.strategy.deserializer.FieldListValueDeserializer;
import br.com.south.batch.sales.strategy.deserializer.FieldValueDeserializerGenerator;
import br.com.south.batch.sales.strategy.deserializer.FieldValueExceptDefaultDeserializer;
import br.com.south.batch.sales.strategy.deserializer.FieldValueNumberDeserializer;
import br.com.south.batch.sales.validator.FieldValueValidator;

import java.lang.reflect.Field;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_FACTORY_CLASS;
import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_INVALID_FACTORY_TYPE;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

public class FieldValueDeserializerGeneratorFactory {

    private FieldValueDeserializerGeneratorFactory() {
        throw new IllegalArgumentException(getMessage(ERROR_FACTORY_CLASS));
    }

    public static FieldValueDeserializerGenerator create(Field field) {
        FieldValueValidator.validate(field);

        PositionalField annotation = field.getAnnotation(PositionalField.class);
        switch (annotation.type()) {
            case TEXT:
            case FIXED:
                return new FieldValueExceptDefaultDeserializer();
            case LIST:
                return new FieldListValueDeserializer();
            case NUMBER:
                return new FieldValueNumberDeserializer();
            default:
                throw new IllegalArgumentException(getMessage(ERROR_INVALID_FACTORY_TYPE));
        }
    }
}
