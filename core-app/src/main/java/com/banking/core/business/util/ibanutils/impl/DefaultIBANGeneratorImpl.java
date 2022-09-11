package com.banking.core.business.util.ibanutils.impl;

import com.banking.core.business.util.ibanutils.IbanGenerator;
import com.banking.core.config.RectifierMapConfig;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DefaultIBANGeneratorImpl implements IbanGenerator {

    private final RectifierMapConfig rectifierMapConfig;
    private final Map<String, List<String>> rectifierMap;
    public DefaultIBANGeneratorImpl(RectifierMapConfig rectifierMapConfig) {
        this.rectifierMapConfig = rectifierMapConfig;
        rectifierMap = rectifierMapConfig.getRectifierMap();
    }

    @Override
    public Iban generate(String countryCode) {
        return new Iban.Builder()
                .countryCode(CountryCode.getByCode(countryCode))
                .buildRandom();
    }

}
