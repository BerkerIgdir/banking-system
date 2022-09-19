package com.banking.core.business.transaction.business.services;

import com.banking.core.business.transaction.business.services.api.TransactionService;

import java.math.BigDecimal;

public class InMemoryTransactionServiceImpl implements TransactionService {

    @Override
    public void beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) {

    }
}
