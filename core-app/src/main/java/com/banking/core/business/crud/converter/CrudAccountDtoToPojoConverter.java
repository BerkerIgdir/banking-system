package com.banking.core.business.crud.converter;

import com.banking.core.business.crud.dto.AccountCrudDTO;
import com.banking.core.business.util.ibanutils.IbanGenerator;
import com.banking.core.config.RectifierMapConfig;
import com.banking.core.dao.entity.Account;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class CrudAccountDtoToPojoConverter implements Converter<AccountCrudDTO.AccountCrudRequestDto, Account> {
    private final IbanGenerator ibanGenerator;
    private final RectifierMapConfig rectifierMapConfig;
    public CrudAccountDtoToPojoConverter(IbanGenerator ibanGenerator, RectifierMapConfig rectifierMapConfig) {
        this.ibanGenerator = ibanGenerator;
        this.rectifierMapConfig = rectifierMapConfig;
    }

    @NonNull
    @Override
    public Account convert(@NonNull AccountCrudDTO.AccountCrudRequestDto source) {
        Objects.requireNonNull(source);
        // Terrible usage of a map.
        var alpha3CountryCode = rectifierMapConfig.getMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(source.country()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();

        return new Account(UUID.randomUUID(),
                source.name(),
                source.surname(),
                ibanGenerator.generate(source.country()).toFormattedString(),
                source.country(),
                BigDecimal.ZERO);
    }
}
