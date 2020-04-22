package br.com.south.batch.sales.exceptions;

public class SalesReportException extends Exception {
    public SalesReportException(String message, Exception cause) {
        super(message, cause);
    }
}
