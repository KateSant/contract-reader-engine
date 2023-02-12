package com.thinktalkbuild.contractreader.api.engine.service;

import com.thinktalkbuild.contractreader.api.engine.model.ContractSummary;
import com.thinktalkbuild.contractreader.api.engine.model.ContractSummarySection;
import com.thinktalkbuild.contractreader.api.engine.model.config.ContractSummaryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author kate
 */
@Service
public class ContractSummariser {

    private Highlighter highligher;
    private ContractSummaryConfig config;

    @Autowired
    ContractSummariser(Highlighter highligher, ContractSummaryConfig config){
        this.highligher=highligher;
        this.config = config;

    }

    public ContractSummary summarise(List<String> inputParagraphs){

        ContractSummary summary = new ContractSummary();
        config.getSections().stream().forEach(sc -> {
            ContractSummarySection section = generateSummarySection(inputParagraphs, sc);
            summary.addSection(section);
        });
        return summary;

    }
    public ContractSummarySection generateSummarySection(List<String> inputParagraphs, ContractSummaryConfig.Section section){
        List<String> interestingParagraphs = findParagraphsContainingAnyOfTheseWords(inputParagraphs, section.getSearchWords());
        List<String> highlightedParagraphs = highligher.highlight(interestingParagraphs, section.getSearchWords());
        return new ContractSummarySection(section.getTitle(), highlightedParagraphs);
    }

    protected List<String> findParagraphsContainingAnyOfTheseWords(List<String> inputParagraphs, List<String> words) {
        return inputParagraphs.stream().filter (
                 para -> containsOneOrMore(para, words))
                .collect(Collectors.toList());
    }

    private boolean containsOneOrMore(String paragraph, List<String> words) {
        return words.stream().anyMatch(word -> paragraph.toLowerCase().contains(word.toLowerCase()));// return early, found one.  Possible future dev: continue searching e.g. to highlight all?
    }
}