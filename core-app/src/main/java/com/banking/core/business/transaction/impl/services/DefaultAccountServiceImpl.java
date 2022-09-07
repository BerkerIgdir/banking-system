package com.banking.core.business.transaction.impl.services;

import com.banking.core.business.exception.AccountNotFoundException;
import com.banking.core.business.transaction.TransactionService;
import com.banking.core.dao.entity.Transaction;
import com.banking.core.dao.repo.AccountRepository;
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
@Qualifier(value = "default-transaction-service")
@Transactional(readOnly = true)
public class DefaultAccountServiceImpl implements TransactionService {

    private final static Logger LOG = LoggerFactory.getLogger(DefaultAccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final TransactionsRepo transactionsRepo;

    public DefaultAccountServiceImpl(AccountRepository repository, TransactionsRepo transactionsRepo) {
        this.accountRepository = repository;
        this.transactionsRepo = transactionsRepo;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
        var transactionToPersist = new Transaction(UUID.randomUUID(), LocalDateTime.now(), fromAcc.getUuid(), toAcc.getUuid(), amount, fromAcc.getCountry());
        transactionsRepo.save(transactionToPersist);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void beginTransaction(
            String fromIban, String toIban, long cents) {

        long fromBalance = accountRepository.getBalance(fromIban);
        LOG.info("Beginning balance of from account is: " + fromBalance);

        if (fromBalance >= cents) {
            accountRepository.addBalance(
                    fromIban, (-1) * cents
            );

            accountRepository.addBalance(
                    toIban, cents
            );
        }
    }
}
