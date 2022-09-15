package com.banking.core.business.transaction.enums;

import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Arrays;

public enum Currency {
    EURO("EUR"), DOLLAR("USD"), LIRA("TRY");
    private final String symbol;

    public String getSymbol() {
        return symbol;
    }
    public static Currency getCurrencyOutOfSymbol(String symbol){
        return Arrays.stream(values())
                .filter(currency -> currency.getSymbol().equals(symbol))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
    Currency(String symbol) {
        this.symbol = symbol;
    }
}
