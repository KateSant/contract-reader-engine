package com.thinktalkbuild.contractreader.engine.model.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "obligations")
public class ObligationsConfig {

    private List<String> obligingVerbs;

    private List<String> nonParties;

    public List<String> getObligingVerbs() {
        return obligingVerbs;
    }

    public void setObligingVerbs(List<String> obligingVerbs) {

        this.obligingVerbs = obligingVerbs;
    }

    public List<String> getNonParties() {
        return nonParties;
    }

    public void setNonParties(List<String> nonParties) {
        this.nonParties = nonParties;
    }
}