package com.banking.core.dao.repo;

import com.banking.core.dao.entity.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AccountTransactionRepository<T extends Account> extends AccountRepository {

    @Query(value = """
        SELECT balance
        FROM public.accounts
        WHERE iban = :iban
        """,
            nativeQuery = true)
    long getBalance(@Param("iban") String iban);

    @Query(value = """
        UPDATE accounts
        SET balance = balance + :cents
        WHERE iban = :iban
        """,
            nativeQuery = true)
    @Modifying
    void addBalance(@Param("iban") String iban, @Param("cents") long cents);
}
