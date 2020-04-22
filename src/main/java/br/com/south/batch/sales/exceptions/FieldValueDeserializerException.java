package br.com.south.batch.sales.exceptions;

public class FieldValueDeserializerException extends Exception {
    public FieldValueDeserializerException(String message) {
        super(message);
    }

    public FieldValueDeserializerException(String message, Exception cause) {
        super(message, cause);
    }
}
