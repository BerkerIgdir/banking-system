package com.banking.core.business.crud;

import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.dao.entity.Account;

import java.util.List;

public interface AccountCrudService {
    String create(AccountCrudDTO.AccountCrudRequestDto accountCrudRequestDto);
    List<Account> retrieveAccountsByCountryCode(String country);
    Account retrieveAccountByIban(String iban);
    void update(AccountCrudDTO.AccountCrudRequestDto accountCrudRequestDto);
    void delete(AccountCrudDTO.AccountCrudRequestDto accountCrudRequestDto);
}
