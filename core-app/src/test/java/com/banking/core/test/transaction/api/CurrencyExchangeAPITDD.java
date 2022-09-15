package com.banking.core.test.transaction.api;

import com.banking.core.test.transaction.api.dto.ApiDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@SpringBootTest
public class CurrencyExchangeAPITDD {
    private static final String BASE_URL = "https://api.apilayer.com/exchangerates_data/convert";
    private static final String QUERY_PARAM_FROM = "from";
    private static final String QUERY_PARAM_TO = "to";
    private static final String QUERY_PARAM_AMOUNT = "amount";
    private static final String API_HEADER = "apikey";
    private final WebClient webClient = WebClient.builder()
            .defaultHeader(API_HEADER, "2AQa9FvujAYB6ubgTRsLYCGYVK4Knj2r")
            .baseUrl(BASE_URL)
            .build();

    @Test
    void uriBuildTest() {
    }

//    {
//        "date": "2022-09-13",
//            "info": {
//        "rate": 0.867126,
//                "timestamp": 1663104484
//    },
//        "query": {
//        "amount": 1,
//                "from": "EUR",
//                "to": "GBP"
//    },
//        "result": 0.867126,
//            "success": true
//    }

    @Test
    void apiCallTest() {
        var response = webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam(QUERY_PARAM_FROM, "EUR")
                                .queryParam(QUERY_PARAM_TO, "USD")
                                .queryParam(QUERY_PARAM_AMOUNT, "10").build())

                .retrieve()
                .onStatus(HttpStatus::isError, ClientResponse::createException)
                .bodyToMono(ApiDto.CurrencyExchangeResponseDto.class)
                .block();

        Assertions.assertNotNull(response);
    }
}
