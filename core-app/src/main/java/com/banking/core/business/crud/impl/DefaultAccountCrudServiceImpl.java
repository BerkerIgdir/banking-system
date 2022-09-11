package com.banking.core.business.crud.impl;

import com.banking.core.business.crud.AccountCrudService;
import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.business.exception.AccountNotFoundException;
import com.banking.core.dao.entity.Account;
import com.banking.core.dao.repo.AccountRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultAccountCrudServiceImpl implements AccountCrudService {
    private final AccountRepository accountRepository;
    private final Converter<AccountCrudDTO.AccountCrudRequestDto, Account> accountCreateRequestDtoAccountConverter;

    public DefaultAccountCrudServiceImpl(AccountRepository accountRepository,
                                         Converter<AccountCrudDTO.AccountCrudRequestDto, Account> accountCreateRequestDtoAccountConverter) {
        this.accountRepository = accountRepository;
        this.accountCreateRequestDtoAccountConverter = accountCreateRequestDtoAccountConverter;
    }

    @Override
    public String create(AccountCrudDTO.AccountCrudRequestDto accountCrudRequestDto) {
       return Optional.ofNullable(accountCrudRequestDto)
                .map(accountCreateRequestDtoAccountConverter::convert)
                .map(accountRepository::save)
                .map(Account::getIBAN)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Account> retrieveAccountsByCountryCode(String countryCode) {
        return accountRepository.findAccountsByCountry(countryCode);
    }

    @Override
    public Account retrieveAccountByIban(String iban) {
        return accountRepository.findAccountByIBAN(iban).orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public void update(AccountCrudDTO.AccountCrudRequestDto accountCrudRequestDto) {

    }

    @Override
    public void delete(AccountCrudDTO.AccountCrudRequestDto accountCrudRequestDto) {

    }


}
