package com.banking.core.test.transaction.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface ApiDto {
    @JsonIgnoreProperties(ignoreUnknown = true)
    record CurrencyExchangeResponseDto(@JsonProperty("date") LocalDate date,
                                       @JsonProperty("result") double result,
                                       @JsonProperty("success") boolean success,
                                       @JsonProperty("info") InfoDto infoDto) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record InfoDto(@JsonProperty("rate") double date,
                   @JsonProperty("timestamp") long timeStamp) { }
}
