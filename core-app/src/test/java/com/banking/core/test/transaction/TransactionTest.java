package com.banking.core.test.transaction;

import com.banking.core.business.transaction.impl.services.DefaultAccountServiceImpl;
import com.banking.core.business.transaction.impl.facades.RetryableAccountFacade;
import com.banking.core.dao.entity.Account;

import com.banking.core.dao.repo.AccountRepository;
import com.banking.core.test.transaction.util.RunnableExceptionUnchecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@SpringBootTest
class TransactionTest {
    private static final int THREAD_NUMBER = 5;
    private static final long TRANSACTION_AMOUNT = 10L;
    private static final String FROM_IBAN = "TR320010009999901234567891";
    private static final String TO_IBAN = "TR320010009999901234567890";

    @Autowired
    private DefaultAccountServiceImpl service;
    @Autowired
    private RetryableAccountFacade retryableAccountService;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void transactionTest() throws InterruptedException {
        var beginningFromBalance = getBalance(FROM_IBAN);
        var beginningToBalance = getBalance(TO_IBAN);

        multiThreadedTransaction(runnableCheckedExceptionSuppress(() -> service.beginTransaction(FROM_IBAN, TO_IBAN, TRANSACTION_AMOUNT)));

        Assertions.assertEquals(getBalance(FROM_IBAN).longValue(), beginningFromBalance.longValue() - TRANSACTION_AMOUNT);
        Assertions.assertEquals(getBalance(TO_IBAN).longValue(), beginningToBalance.longValue() + TRANSACTION_AMOUNT);
    }

    @Test
    void transactionTest_Retryable() throws InterruptedException {
        var beginningFromBalance = getBalance(FROM_IBAN);
        var beginningToBalance = getBalance(TO_IBAN);
        multiThreadedTransaction(runnableCheckedExceptionSuppress(() -> retryableAccountService.beginTransaction(FROM_IBAN, TO_IBAN, new BigDecimal(TRANSACTION_AMOUNT))));
        Assertions.assertEquals(beginningFromBalance.longValue() - (TRANSACTION_AMOUNT * THREAD_NUMBER), getBalance(FROM_IBAN).longValue());
        Assertions.assertEquals(beginningToBalance.longValue() + (TRANSACTION_AMOUNT * THREAD_NUMBER), (getBalance(TO_IBAN).longValue()));
    }

    private <T extends Number> void multiThreadedTransaction(Runnable method) throws InterruptedException {
        var startLatch = new CountDownLatch(1);
        var endLatch = new CountDownLatch(THREAD_NUMBER);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    startLatch.await();
                    method.run();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    endLatch.countDown();
                }
            }).start();
        }

        startLatch.countDown();
        endLatch.await();
    }

    private Runnable runnableCheckedExceptionSuppress(RunnableExceptionUnchecker<Exception> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private Number getBalance(String IBAN) {
        return accountRepository.findAccountByIBAN(IBAN).map(Account::getBalance).orElseThrow();
    }
}

