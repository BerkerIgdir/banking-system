package com.banking.core.business.crud;

import com.banking.core.business.crud.dto.AccountCreateRequestDto;

public interface AccountCrudService {
    void create(AccountCreateRequestDto accountCreateRequestDto);
    void remove(AccountCreateRequestDto accountCreateRequestDto);
    void update(AccountCreateRequestDto accountCreateRequestDto);
}
