package com.banking.core.business.util.ibanutils.impl;

import com.banking.core.business.util.ibanutils.IbanGenerator;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Component;

@Component
public class DefaultIBANGeneratorImpl implements IbanGenerator {
    @Override
    public Iban generate(String countryCode) {
        return new Iban.Builder()
                .countryCode(CountryCode.getByCode(countryCode))
                .buildRandom();
    }
}
