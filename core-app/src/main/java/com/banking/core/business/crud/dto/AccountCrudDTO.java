package com.banking.core.business.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface AccountCrudDTO {

    record AccountCrudRequestDto(@JsonProperty("name") @NotEmpty @NotNull String name,
                                 @JsonProperty("surname") @NotEmpty @NotNull String surname,
                                 @JsonProperty("country") @NotEmpty @NotNull String country) {
    }

    record AccountCrudResponseDto(@JsonProperty("name") String name,
                                  @JsonProperty("surname") String surname,
                                  @JsonProperty("country") String country,
                                  @JsonProperty("iban") String IBAN,
                                  @JsonProperty("balance") BigDecimal balance) {
    }
}
