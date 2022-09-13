package com.banking.core.business.crud.converter;

import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.business.util.ibanutils.IbanGenerator;
import com.banking.core.dao.entity.Account;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Component
public class CrudAccountDtoToPojoConverter implements Converter<AccountCrudDTO.AccountCreateRequestDto, Account> {
    private final IbanGenerator ibanGenerator;
    public CrudAccountDtoToPojoConverter(IbanGenerator ibanGenerator) {
        this.ibanGenerator = ibanGenerator;
    }

    @NonNull
    @Override
    public Account convert(@NonNull AccountCrudDTO.AccountCreateRequestDto source) {
        Objects.requireNonNull(source);
        return new Account(UUID.randomUUID(),
                source.name(),
                source.surname(),
                ibanGenerator.generate(source.country()).toString(),
                source.country(),
                BigDecimal.ZERO);
    }
}
