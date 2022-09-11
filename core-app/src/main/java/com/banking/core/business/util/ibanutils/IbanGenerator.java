package com.banking.core.business.util.ibanutils;

import org.iban4j.Iban;

import java.util.Locale;

public interface IbanGenerator {
    Iban generate(String countryCode);
}
