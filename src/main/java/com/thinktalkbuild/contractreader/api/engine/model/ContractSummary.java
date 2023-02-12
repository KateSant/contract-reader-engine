package com.thinktalkbuild.contractreader.api.engine.model;

import java.util.ArrayList;
import java.util.List;

public class ContractSummary {

    // todo - some metadata here? e.g. filename, date of processing?

    private List<ContractSummarySection> sections = new ArrayList<>();
    public void addSection(ContractSummarySection s){
        sections.add(s);
    }

    public List<ContractSummarySection> getSections() {
        return sections;
    }
}
