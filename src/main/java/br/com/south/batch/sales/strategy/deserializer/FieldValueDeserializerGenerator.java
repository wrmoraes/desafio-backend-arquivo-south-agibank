package br.com.south.batch.sales.strategy.deserializer;


import br.com.south.batch.sales.exceptions.FieldValueDeserializerException;

import java.lang.reflect.Field;

public interface FieldValueDeserializerGenerator {
    Object generate(Field field, String line, String delimiter) throws FieldValueDeserializerException;
}
