package br.com.south.batch.sales.exceptions;

public class FindWorstSellerException extends Exception {
    public FindWorstSellerException(String message) {
        super(message);
    }

    public FindWorstSellerException(String message, Exception cause) {
        super(message, cause);
    }
}
