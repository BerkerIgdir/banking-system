package com.banking.core.dao.projection;

import java.math.BigDecimal;

public record AccountProjectionRecord(String name, String surname, String IBAN, String currency, BigDecimal balance) {
}
