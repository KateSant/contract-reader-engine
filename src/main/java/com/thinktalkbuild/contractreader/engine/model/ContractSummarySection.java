package com.thinktalkbuild.contractreader.engine.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kate
 */
public class ContractSummarySection {
    
    private String title;
    
    private List<String> resultsParagraphs = new ArrayList<>();

    public ContractSummarySection(String title, List<String> resultsParagraphs) {
        this.title = title;
        this.resultsParagraphs = resultsParagraphs;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getResultsParagraphs() {
        return resultsParagraphs;
    }

}
