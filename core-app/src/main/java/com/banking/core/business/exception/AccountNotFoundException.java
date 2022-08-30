package com.banking.core.business.exception;

public class AccountNotFoundException extends RuntimeException {
    protected static final String ACCOUNT_NOT_EXIST = "ACCOUNT DOES NOT EXIST!";

    public AccountNotFoundException() {
        super(ACCOUNT_NOT_EXIST);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
