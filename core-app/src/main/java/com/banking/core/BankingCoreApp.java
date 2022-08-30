package com.banking.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.banking")
public class BankingCoreApp {
    public static void main(String[] args) {
        SpringApplication.run(BankingCoreApp.class);
    }
}
