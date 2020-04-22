package br.com.south.batch.sales.strategy.deserializer;

import br.com.south.batch.sales.annotation.PositionalField;
import br.com.south.batch.sales.exceptions.FieldValueDeserializerException;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static br.com.south.batch.sales.utils.message.MessageCodeEnum.ERROR_DESERIALIZER_CONVERTER;
import static br.com.south.batch.sales.utils.message.MessageUtils.getMessage;

public class FieldValueNumberDeserializer implements FieldValueDeserializerGenerator {

    public BigDecimal generate(Field field, String line, String delimiter) throws FieldValueDeserializerException {
        PositionalField positionalFieldAnn = field.getAnnotation(PositionalField.class);

        try {
            return new BigDecimal(line.split(delimiter)[positionalFieldAnn.position() - 1]);
        } catch (Exception e) {
            throw new FieldValueDeserializerException(getMessage(ERROR_DESERIALIZER_CONVERTER,
                field.getName(), field.getDeclaringClass(), e.getMessage()), e);
        }
    }
}
