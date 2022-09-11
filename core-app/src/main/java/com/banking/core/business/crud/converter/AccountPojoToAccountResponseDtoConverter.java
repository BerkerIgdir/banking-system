package com.banking.core.business.crud.converter;

import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.dao.entity.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountPojoToAccountResponseDtoConverter implements Converter<Account, AccountCrudDTO.AccountCrudResponseDto> {

    @Override
    public AccountCrudDTO.AccountCrudResponseDto convert(@NonNull Account source) {
        Objects.requireNonNull(source);
        return null;
    }

}
