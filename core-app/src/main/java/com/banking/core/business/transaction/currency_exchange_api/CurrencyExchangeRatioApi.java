package com.banking.core.business.transaction.currency_exchange_api;

import com.banking.core.business.transaction.enums.Currency;

import java.math.BigDecimal;

public interface CurrencyExchangeRatioApi<T extends Number> {
    BigDecimal getExchangeRatio(Currency ofCurrency, Currency toCurrency, T amount);
}
