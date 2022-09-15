package com.banking.core.dao.repo;

import com.banking.core.dao.entity.Account;
import com.banking.core.dao.projection.AccountProjection;
import com.banking.core.dao.projection.AccountProjectionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Query(value = """
            select
            a.name as name ,
            a.surname as surname ,
            a.IBAN as iban ,
            a.balance as balance,
            c.currency as currency
            from public.accounts as a
            join public.countries as c
            on a.country_code = c.code
            where a.IBAN = :iban
            """, nativeQuery = true)
    Optional<AccountProjection> readAccountByIban(@Param("iban") String iban);

    Optional<Account> findAccountByIBAN(String iban);

    @Query(value = """
            SELECT *
            FROM public.accounts
            WHERE country_code = :countryCode
            """,
            nativeQuery = true)
    List<Account> findAccountsByCountry(@Param("countryCode") String countryCode);

    List<Account> findAccountsByNameAndSurname(String name, String surname);
}
