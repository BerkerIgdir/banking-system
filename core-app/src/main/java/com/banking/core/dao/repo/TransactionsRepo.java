package com.banking.core.dao.repo;

import com.banking.core.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionsRepo extends JpaRepository<Transaction, UUID> {
    @Query(value = """
            SELECT * FROM public.transactions
            where to_account = :toIban AND from_account = :fromIban
            """, nativeQuery = true)
    List<Transaction> findTransactionsByIbans(@Param(value = "fromIban") String fromIban, @Param(value = "toIban") String toIban);
}
