package br.com.south.batch.sales.exceptions;

public class FieldValueSerializerException extends Exception {
    public FieldValueSerializerException(String message) {
        super(message);
    }

    public FieldValueSerializerException(String message, Exception cause) {
        super(message, cause);
    }
}
