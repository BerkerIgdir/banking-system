package com.banking.core.business.exception;

public class MaxTransactionsAttemptException extends RuntimeException {
    private static final String DEFAULT_EXCEPTION_MESSAGE = "NUMBER OF MAX RETRY ATTEMPTS IS EXCEEDED";
    public MaxTransactionsAttemptException() {
        super(DEFAULT_EXCEPTION_MESSAGE);
    }
    public MaxTransactionsAttemptException(String message){
        super(message);
    }
    public MaxTransactionsAttemptException(Throwable e){
        super(e);
    }
}
