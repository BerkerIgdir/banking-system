package com.banking.core.controller.transaction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface TransactionApiDto {
    @JsonIgnoreProperties(ignoreUnknown = true)
    record TransactionRequestDto(@JsonProperty("from") @NotEmpty @NotNull String fromIban,
                                 @JsonProperty("to") @NotEmpty @NotNull String toIban,
                                 @JsonProperty("amount") @NotNull BigDecimal amount) {
    }
}
