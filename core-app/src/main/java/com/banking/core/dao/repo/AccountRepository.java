package com.banking.core.dao.repo;

import com.banking.core.dao.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAccountByIBAN(String iban);
    List<Account> findAccountsByNameAndSurname(String name, String surname);
}
