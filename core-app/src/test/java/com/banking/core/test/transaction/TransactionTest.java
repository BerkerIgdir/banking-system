package com.banking.core.test.transaction;

import com.banking.core.business.transaction.TransactionService;
import com.banking.core.business.transaction.impl.DefaultAccountServiceImpl;
import com.banking.core.dao.entity.Account;

import com.banking.core.dao.repo.AccountRepository;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Repeat;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class TransactionTest {
    private static final int THREAD_NUMBER = 5;
    private static final String FROM_IBAN = "TR320010009999901234567890";
    private static final String TO_IBAN = "TR320010009999901234567891";
    @Autowired
    private DefaultAccountServiceImpl service;
    @Autowired
    private AccountRepository accountRepository;

    @Test
//    @RepeatedTest(5)
    void transactionTest() throws InterruptedException {
        var startLatch = new CountDownLatch(1);
        var endLatch = new CountDownLatch(THREAD_NUMBER);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    service.transfer(FROM_IBAN, TO_IBAN, 20l);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }
        startLatch.countDown();
        endLatch.await();
        accountRepository.findAccountByIBAN(FROM_IBAN).ifPresent(account -> System.out.println("From account balance is: " + account.getBalance()));
        accountRepository.findAccountByIBAN(TO_IBAN).ifPresent(account -> System.out.println("To account balance is: " + account.getBalance()));
    }
}
