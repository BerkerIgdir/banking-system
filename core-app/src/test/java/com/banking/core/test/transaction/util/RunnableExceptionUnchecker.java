package com.banking.core.test.transaction.util;

public interface RunnableExceptionUnchecker<E extends Exception> {
    void run() throws E;
}
