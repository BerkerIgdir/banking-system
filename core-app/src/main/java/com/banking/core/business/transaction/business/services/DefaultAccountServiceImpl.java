package com.banking.core.business.transaction.business.services;

import com.banking.core.business.exception.AccountNotFoundException;
import com.banking.core.business.transaction.business.services.api.AbstractTransactionService;
import com.banking.core.business.transaction.currency_exchange_api.CurrencyExchangeRatioApi;
import com.banking.core.business.transaction.enums.Currency;
import com.banking.core.dao.entity.Account;
import com.banking.core.dao.entity.Transaction;
import com.banking.core.dao.projection.AccountProjection;
import com.banking.core.dao.repo.AccountTransactionRepository;
import com.banking.core.dao.repo.TransactionsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Qualifier(value = "default-transaction-service")
@Transactional(readOnly = true)
public class DefaultAccountServiceImpl extends AbstractTransactionService {

    private final static Logger LOG = LoggerFactory.getLogger(DefaultAccountServiceImpl.class);

    public DefaultAccountServiceImpl(AccountTransactionRepository<Account> repository, TransactionsRepo transactionsRepo, CurrencyExchangeRatioApi<BigDecimal> currencyExchangeRatioApiBigDecimal) {
        super(repository, transactionsRepo, currencyExchangeRatioApiBigDecimal);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void beginTransaction(String fromIban, String toIban, BigDecimal cents) {
        var fromAccProjectionFuture = findByIbanAsync(fromIban);
        var toAccProjectionFuture = findByIbanAsync(toIban);

        var fromAccProjection = fromAccProjectionFuture.join();
        var toAccProjection = toAccProjectionFuture.join();

        var fromCurrency = Currency.getCurrencyOutOfSymbol(fromAccProjection.getCurrency());
        var toCurrency = Currency.getCurrencyOutOfSymbol(toAccProjection.getCurrency());
        var amount = currencyExchangeRatioApi.getExchangeRatio(fromCurrency, toCurrency, cents);

        LOG.info("Beginning balance of from account is: " + fromAccProjection.getBalance());

        if (fromAccProjection.getBalance().compareTo(amount) >= 0) {
            accountRepository.addBalance(fromIban, amount.negate());
            accountRepository.addBalance(toIban, amount);
        }
        var transactionToPersist = new Transaction(UUID.randomUUID(), Instant.now().toEpochMilli(), fromIban, toIban, cents);
        transactionsRepo.save(transactionToPersist);
    }

    private CompletableFuture<AccountProjection> findByIbanAsync(String iban) {
        return CompletableFuture.supplyAsync(() -> accountRepository.readAccountByIban(iban))
                .thenApplyAsync(accountProjection -> accountProjection.orElseThrow(AccountNotFoundException::new))
                .exceptionally(throwable -> {
                    throw new RuntimeException(throwable);
                });
    }
}
