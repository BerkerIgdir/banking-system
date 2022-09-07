package com.banking.common.module.retryconfig;

import com.banking.core.config.RetryConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@ConfigurationProperties(prefix = "spring-retry")
public class RetryConfigData {
    private final RetryConfigProperties retryConfigProperties;

    public RetryConfigData(RetryConfigProperties retryConfigProperties) {
        this.retryConfigProperties = retryConfigProperties;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        var retryTemplate = new RetryTemplate();

        var backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(retryConfigProperties.getInitialIntervalMs());
        backOffPolicy.setMultiplier(retryConfigProperties.getMultiplier());
        backOffPolicy.setMaxInterval(retryConfigProperties.getMaxIntervalMs());

        retryTemplate.setBackOffPolicy(backOffPolicy);

        var retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(retryConfigProperties.getMaxAttempts());

        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}
