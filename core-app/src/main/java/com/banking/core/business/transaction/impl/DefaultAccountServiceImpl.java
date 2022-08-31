package com.banking.core.business.transaction.impl;

import com.banking.core.business.exception.AccountNotFoundException;
import com.banking.core.business.transaction.TransactionService;
import com.banking.core.dao.entity.Account;
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
    @Transactional
    public void beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) {
        var fromAcc = accountRepository.findAccountByIBAN(fromIBAN).orElseThrow(AccountNotFoundException::new);
        var toAcc = accountRepository.findAccountByIBAN(toIBAN).orElseThrow(AccountNotFoundException::new);

        System.out.println(Thread.currentThread() + " Beginning balance of from account is: " + fromAcc.getBalance());
        System.out.println(Thread.currentThread() + "Beginning balance of to account is: " + toAcc.getBalance());

        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException(Thread.currentThread().getName() + " NOT ENOUGH BALANCE!");
        }
        var newFromBalance = accountRepository.findAccountByIBAN(fromIBAN).map(Account::getBalance).map(blnc -> blnc.subtract(amount)).orElseThrow();
        fromAcc.setBalance(new BigDecimal(accountRepository.getBalance(fromIBAN)));
        var newToBalance = accountRepository.findAccountByIBAN(toIBAN).map(Account::getBalance).map(blnc -> blnc.add(amount)).orElseThrow();
        var newToBalanceSql = accountRepository.getBalance(toIBAN);
        toAcc.setBalance(new BigDecimal(newToBalanceSql));
        var transactionToPersist = new Transaction(UUID.randomUUID(), LocalDateTime.now(), fromAcc.getUuid(), toAcc.getUuid(), amount, fromAcc.getCountry());
        transactionsRepo.save(transactionToPersist);
    }
    @Transactional
    public boolean transfer(
            String fromIban, String toIban, long cents) {
        boolean status = true;

        long fromBalance = accountRepository.getBalance(fromIban);
        System.out.println(Thread.currentThread() + " Beginning balance of from account is: " + fromBalance);
        if(fromBalance >= cents) {
            status &= accountRepository.addBalance(
                    fromIban, (-1) * cents
            ) > 0;

            status &= accountRepository.addBalance(
                    toIban, cents
            ) > 0;
        }

        return status;
    }
}
