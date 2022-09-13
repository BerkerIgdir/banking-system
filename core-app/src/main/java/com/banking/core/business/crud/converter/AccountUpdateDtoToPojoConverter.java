package com.banking.core.business.crud.converter;

import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.business.exception.AccountNotFoundException;
import com.banking.core.dao.entity.Account;
import com.banking.core.dao.repo.AccountRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AccountUpdateDtoToPojoConverter implements Converter<AccountCrudDTO.AccountUpdateRequestDto,Account> {
    private final AccountRepository accountRepository;

    public AccountUpdateDtoToPojoConverter(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account convert(AccountCrudDTO.AccountUpdateRequestDto source) {
        Objects.requireNonNull(source);
        var oldPojo = accountRepository.findAccountByIBAN(source.iban()).orElseThrow(AccountNotFoundException::new);
        Optional.ofNullable(source.name()).ifPresent(oldPojo::setName);
        Optional.ofNullable(source.surname()).ifPresent(oldPojo::setSurname);
        Optional.ofNullable(source.balance()).ifPresent(oldPojo::setBalance);
        Optional.ofNullable(source.country()).ifPresent(oldPojo::setCountry);
        return oldPojo;
    }
}
