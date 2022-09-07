package com.banking.core.business.transaction.impl.services;

import com.banking.core.business.transaction.TransactionService;

import java.math.BigDecimal;

public class InMemoryTransactionServiceImpl implements TransactionService {

    @Override
    public void beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) {

    }
}
