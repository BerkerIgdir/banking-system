package com.banking.core.dao.repo;

import com.banking.core.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionsRepo extends JpaRepository<Transaction, UUID> {

}
