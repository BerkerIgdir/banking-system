package com.banking.core.business.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountCreateRequestDto(@JsonProperty("name") String name,@JsonProperty("surname") String surname,@JsonProperty("country") String country) { }
