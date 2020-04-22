package br.com.south.batch.sales.exceptions;

public class FindMostExpensiveException extends Exception {
    public FindMostExpensiveException(String message) {
        super(message);
    }

    public FindMostExpensiveException(String message, Exception cause) {
        super(message, cause);
    }
}
