package com.banking.core.business.transaction.business.services.api;

import com.banking.core.business.transaction.currency_exchange_api.CurrencyExchangeRatioApi;
import com.banking.core.dao.entity.Account;
import com.banking.core.dao.entity.Transaction;
import com.banking.core.dao.repo.AccountTransactionRepository;
import com.banking.core.dao.repo.TransactionsRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractTransactionService implements TransactionService {
    protected final AccountTransactionRepository<Account> accountRepository;
    protected final TransactionsRepo transactionsRepo;
    protected final CurrencyExchangeRatioApi<BigDecimal> currencyExchangeRatioApi;

    protected AbstractTransactionService(AccountTransactionRepository<Account> accountRepository, TransactionsRepo transactionsRepo, CurrencyExchangeRatioApi<BigDecimal> currencyExchangeRatioApi) {
        this.accountRepository = accountRepository;
        this.transactionsRepo = transactionsRepo;
        this.currencyExchangeRatioApi = currencyExchangeRatioApi;
    }

    @Override
    abstract public Transaction beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) throws InterruptedException;

    @Override
    public Transaction getTransaction(UUID uuid) {
        return Optional.ofNullable(transactionsRepo.getReferenceById(uuid)).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Transaction> getTransactions(String fromIban, String toIban) {
        return transactionsRepo.findTransactionsByIbans(fromIban, toIban);
    }
}
