package com.banking.core.business.transaction.business.facades;

import com.banking.core.business.exception.MaxTransactionsAttemptException;
import com.banking.core.business.transaction.business.services.api.TransactionService;
import com.banking.core.config.RetryConfigProperties;
import com.banking.core.dao.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class RetryableAccountFacade {

    private static final Logger LOG = LoggerFactory.getLogger(RetryableAccountFacade.class);

    private final RetryTemplate retryTemplate;
    private final RetryConfigProperties retryConfigProperties;
    private final TransactionService transactionService;


    public RetryableAccountFacade(RetryTemplate retryTemplate,
                                  RetryConfigProperties retryConfigProperties,
                                  @Qualifier(value = "jpa-transaction-impl") TransactionService transactionService) {
        this.retryTemplate = retryTemplate;
        this.retryConfigProperties = retryConfigProperties;
        this.transactionService = transactionService;
    }

    public Transaction beginTransaction(String fromIBAN, String toIBAN, BigDecimal amount) throws InterruptedException {
        LOG.info("Retryable transaction is beginning...");
        int currentRetryCount = 1;
        long sleepDuration = 1L;
        double multiplier = retryConfigProperties.getMultiplier();
        while (true) {
            var optionalTransaction = retryTemplate.execute(retryContext -> retryTransaction(retryContext, fromIBAN, toIBAN, amount));

            if (optionalTransaction.isPresent()) {
                return optionalTransaction.get();
            }

            if (currentRetryCount++ > retryConfigProperties.getMaxAttempts()) {
                throw new MaxTransactionsAttemptException();
            }

            sleepDuration += (sleepDuration * multiplier);
            Thread.sleep(sleepDuration);
        }
    }

    private Optional<Transaction> retryTransaction(RetryContext context, String fromIBAN, String toIBAN, BigDecimal amount) {
        try {
            LOG.info("Current retry count is: " + context.getRetryCount());
            return Optional.ofNullable(transactionService.beginTransaction(fromIBAN, toIBAN, amount));
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }
}
