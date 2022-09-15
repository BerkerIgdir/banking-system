package com.banking.core.business.transaction.currency_exchange_api.impl;

import com.banking.core.business.transaction.currency_exchange_api.CurrencyExchangeRatioApi;
import com.banking.core.business.transaction.currency_exchange_api.dto.ApiDto;
import com.banking.core.business.transaction.enums.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Component
public class ExchangeRatesRatioApiClientImpl implements CurrencyExchangeRatioApi<BigDecimal> {
    private static final String BASE_URL = "https://api.apilayer.com/exchangerates_data/convert";
    private static final String QUERY_PARAM_FROM = "from";
    private static final String QUERY_PARAM_TO = "to";
    private static final String QUERY_PARAM_AMOUNT = "amount";
    private static final String API_HEADER = "apikey";
    private final WebClient webClient;

    public ExchangeRatesRatioApiClientImpl(WebClient.Builder webClientBuilder,
                                           @Value("${exchange-api-key}") String apiKey) {

        this.webClient = webClientBuilder.defaultHeader(API_HEADER, apiKey).baseUrl(BASE_URL).build();
    }

    public BigDecimal getExchangeRatio(Currency ofCurrency, Currency toCurrency, BigDecimal amount) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam(QUERY_PARAM_FROM, ofCurrency.getSymbol())
                                .queryParam(QUERY_PARAM_TO, toCurrency.getSymbol())
                                .queryParam(QUERY_PARAM_AMOUNT, amount.toString()).build())
                .retrieve()
                .onStatus(HttpStatus::isError, ClientResponse::createException)
                .bodyToMono(ApiDto.CurrencyExchangeResponseDto.class)
                .block().result();
    }

}
