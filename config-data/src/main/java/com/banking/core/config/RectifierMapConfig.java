package com.banking.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import java.util.*;

@Configuration
@ConfigurationProperties(prefix = "valuemap")
@PropertySource(value = "classpath:country-code-rectifier.yaml")
public class RectifierMapConfig {
    private Map<String, List<String>> rectifierMap;

    public Map<String, List<String>> getMap() {
        return Collections.unmodifiableMap(rectifierMap);
    }

    //The map only can be set by app context
    public synchronized void setMap(Map<String, List<String>> map) {
        if (Objects.nonNull(rectifierMap)) {
            throw new RuntimeException("Map can not be initiliazed twice!");
        }
        rectifierMap = map;
    }
}
