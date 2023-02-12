package com.thinktalkbuild.contractreader.api.engine.model.config;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = ContractSummaryConfig.class)
class ContractSummaryConfigTests {

    @Autowired
    private ContractSummaryConfig applicationProps;

    @Test
    public void testLoadsYaml() {
        assertNotNull(applicationProps.getSections().get(0));

    }
}
