package com.banking.core.business.transaction.impl;

import com.banking.core.business.exception.AccountNotFoundException;
import com.banking.core.business.transaction.TransactionService;
import com.banking.core.dao.entity.Transaction;
import com.banking.core.dao.repo.AccountRepository;
import com.banking.core.dao.repo.TransactionsRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class DefaultAccountServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionsRepo transactionsRepo;

    public DefaultAccountServiceImpl(AccountRepository repository, TransactionsRepo transactionsRepo) {
        this.accountRepository = repository;
        this.transactionsRepo = transactionsRepo;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) {
        var fromAcc = accountRepository.findAccountByIBAN(fromIBAN)
                .orElseThrow(AccountNotFoundException::new);
        var toAcc = accountRepository.findAccountByIBAN(toIBAN)
                .orElseThrow(AccountNotFoundException::new);

        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("NOT ENOUGH BALANCE!");
        }

        fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
        toAcc.setBalance(toAcc.getBalance().add(amount));
        var transactionToPersist = new Transaction(UUID.randomUUID(), LocalDateTime.now(), fromAcc.getUuid(), toAcc.getUuid(), amount);
        transactionsRepo.save(transactionToPersist);
    }
}
