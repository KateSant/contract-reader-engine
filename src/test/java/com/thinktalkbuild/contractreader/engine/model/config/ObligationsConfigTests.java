package com.thinktalkbuild.contractreader.engine.model.config;


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
@EnableConfigurationProperties(value = ObligationsConfig.class)
class ObligationsConfigTests {

    @Autowired
    private ObligationsConfig obligationsConfig;

    @Test
    public void testLoadsYaml() {
        assertNotNull(obligationsConfig.getObligingVerbs().get(0));
        assertNotNull(obligationsConfig.getNonParties().get(0));
    }
}
