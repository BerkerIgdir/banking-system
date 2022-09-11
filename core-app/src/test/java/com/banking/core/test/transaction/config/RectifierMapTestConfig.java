package com.banking.core.test.transaction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "rectifier-config")
@PropertySource(value = "classpath:country-code-rectifier.yaml")
public class RectifierMapTestConfig {
    private Map<String, List<String>> rectifierMap;

    public Map<String, List<String>> getMap() {
        return Collections.unmodifiableMap(rectifierMap);
    }

    public synchronized void setMap(Map<String, List<String>> map) {
        if (Objects.nonNull(rectifierMap)) {
            throw new RuntimeException("Map can not be initiliazed twice!");
        }
        rectifierMap = map;
    }
}
