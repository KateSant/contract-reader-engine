package com.thinktalkbuild.contractreader.api.engine.model;

import lombok.Data;

import java.util.List;

@Data
public class Analysis {

    String fileName;
    ContractSummary summary;
    ObligationsByParty obligationsByParty;
    List<Duration> durations;
}
