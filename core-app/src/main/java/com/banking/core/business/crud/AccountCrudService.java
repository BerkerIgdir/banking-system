package com.banking.core.business.crud;

import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.dao.entity.Account;
import org.iban4j.Iban;

import java.util.List;

public interface AccountCrudService {
    String create(AccountCrudDTO.AccountCreateRequestDto accountCrudRequestDto);
    List<Account> retrieveAccountsByCountryCode(String country);
    Account retrieveAccountByIban(String iban);
    String update(AccountCrudDTO.AccountUpdateRequestDto accountCrudRequestDto);
    void delete(String iban);
}
