package com.banking.core.test.transaction;

import com.banking.core.business.transaction.TransactionService;
import com.banking.core.dao.entity.Account;

import com.banking.core.dao.repo.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class TransactionTest {
    
//    private final static List<Account> accountList = List.of(new Account(UUID.randomUUID(),"DEMO_ACC1","DEMO_ACC_SURNAME_1","IBAN1",))
    @Autowired
    private TransactionService service;
    @Test
    void transactionTest() {

    }
}
