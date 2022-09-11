package com.banking.core.test.transaction;

import com.banking.core.test.transaction.config.RectifierMapTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfigPropertiesTDD {
    @Autowired
    private RectifierMapTestConfig rectifierMapConfig;

    @Test
    void contextLoadTest(){
     rectifierMapConfig.getMap().keySet().forEach(System.out::println);
    }

}
