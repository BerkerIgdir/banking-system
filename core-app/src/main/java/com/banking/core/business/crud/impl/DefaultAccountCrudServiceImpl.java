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
    private final Converter<AccountCrudDTO.AccountCreateRequestDto, Account> accountCreateRequestDtoAccountConverter;
    private final Converter<AccountCrudDTO.AccountUpdateRequestDto, Account> updateRequestDtoAccountConverter;

    public DefaultAccountCrudServiceImpl(AccountRepository accountRepository,
                                         Converter<AccountCrudDTO.AccountCreateRequestDto, Account> accountCreateRequestDtoAccountConverter, Converter<AccountCrudDTO.AccountUpdateRequestDto, Account> updateRequestDtoAccountConverter) {
        this.accountRepository = accountRepository;
        this.accountCreateRequestDtoAccountConverter = accountCreateRequestDtoAccountConverter;
        this.updateRequestDtoAccountConverter = updateRequestDtoAccountConverter;
    }

    @Override
    public String create(AccountCrudDTO.AccountCreateRequestDto accountCrudRequestDto) {
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
    public String update(AccountCrudDTO.AccountUpdateRequestDto accountUpdateRequestDto) {
        var oldPojo = accountRepository.findAccountByIBAN(accountUpdateRequestDto.iban()).orElseThrow(AccountNotFoundException::new);
        return updatePojo(oldPojo, accountUpdateRequestDto);
    }

    private String updatePojo(Account oldPojo,AccountCrudDTO.AccountUpdateRequestDto accountUpdateRequestDto) {
        var updatedPojo = updateRequestDtoAccountConverter.convert(accountUpdateRequestDto);
        return accountRepository.save(updatedPojo).getIBAN();
    }

    @Override
    public void delete(String iban) {
        var pojoToDelete = accountRepository.findAccountByIBAN(iban).orElseThrow(AccountNotFoundException::new);
        accountRepository.delete(pojoToDelete);
    }


}
