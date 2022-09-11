package com.banking.core.business.transaction;

import com.banking.core.dao.entity.Account;

import java.math.BigDecimal;

public interface TransactionService {
    void beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) throws InterruptedException;
}
