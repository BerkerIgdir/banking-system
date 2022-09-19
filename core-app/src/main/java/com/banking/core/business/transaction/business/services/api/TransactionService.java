package com.banking.core.business.transaction.business.services.api;

import com.banking.core.dao.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    Transaction beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) throws InterruptedException;
    Transaction getTransaction(UUID uuid);
    List<Transaction> getTransactions(String fromIban, String toIban);
}
