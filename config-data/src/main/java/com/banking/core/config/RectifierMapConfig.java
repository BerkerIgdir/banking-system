package com.banking.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.*;

@Configuration
@PropertySource(value = "classpath:country-code-rectifier.yaml")
@ConfigurationProperties(prefix = "value-map")
public class RectifierMapConfig {
    private Map<String, List<String>> rectifierMap;

    public Map<String, List<String>> getRectifierMap() {
        return Collections.unmodifiableMap(rectifierMap);
    }

    //The map only can be set by app context
    public synchronized void setRectifierMap(Map<String, List<String>> map) {
        if (Objects.nonNull(rectifierMap)) {
            throw new RuntimeException("Map can not be initiliazed twice!");
        }
        rectifierMap = map;
    }
}
