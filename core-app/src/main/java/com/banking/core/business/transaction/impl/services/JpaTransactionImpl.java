package com.banking.core.business.transaction.impl.services;

import com.banking.core.business.exception.AccountNotFoundException;
import com.banking.core.business.transaction.currency_exchange_api.CurrencyExchangeRatioApi;
import com.banking.core.business.transaction.impl.services.api.AbstractTransactionService;
import com.banking.core.dao.entity.Account;
import com.banking.core.dao.entity.Transaction;
import com.banking.core.dao.repo.AccountTransactionRepository;
import com.banking.core.dao.repo.TransactionsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Qualifier("jpa-transaction-impl")
public class JpaTransactionImpl extends AbstractTransactionService {

    private final static Logger LOG = LoggerFactory.getLogger(JpaTransactionImpl.class);


    public JpaTransactionImpl(AccountTransactionRepository<Account> accountRepository, TransactionsRepo transactionsRepo, CurrencyExchangeRatioApi<BigDecimal> currencyExchangeRatioApi) {
        super(accountRepository, transactionsRepo, currencyExchangeRatioApi);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) {
        var fromAcc = accountRepository.findAccountByIBAN(fromIBAN).orElseThrow(AccountNotFoundException::new);
        var toAcc = accountRepository.findAccountByIBAN(toIBAN).orElseThrow(AccountNotFoundException::new);

        LOG.info("Beginning balance of from account is: " + fromAcc.getBalance());
        LOG.info("Beginning balance of to account is: " + toAcc.getBalance());

        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(Thread.currentThread().getName() + " NOT ENOUGH BALANCE!");
        }

        fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
        toAcc.setBalance(toAcc.getBalance().add(amount));
        var transactionToPersist = new Transaction(UUID.randomUUID(), LocalDateTime.now(), fromAcc.getIBAN(), toAcc.getIBAN(), amount);
        transactionsRepo.save(transactionToPersist);
    }
}
