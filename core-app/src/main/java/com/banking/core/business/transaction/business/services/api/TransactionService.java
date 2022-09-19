package com.banking.core.business.transaction.business.services.api;

import java.math.BigDecimal;

public interface TransactionService {
    void beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) throws InterruptedException;
}
