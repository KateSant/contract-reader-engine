package com.thinktalkbuild.contractreader.api.engine.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.thinktalkbuild.contractreader.api.engine.model.ContractSummarySection;
import com.thinktalkbuild.contractreader.api.engine.model.config.ContractSummaryConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * @author kate
 */
public class ContractSummariserTests {


    private ContractSummariser summariser;

    @BeforeEach
    void setup(){
        summariser = new ContractSummariser(new Highlighter(), null);
    }

    static List<String> DUMMY_INPUT_DATA = new ArrayList<>(List.of(
            "This is the first paragraph, it contains the word apple.",
            "This is the second paragraph, it contains the words apple and banana and cherry.",
            "This is the third paragraph, it contains no fruit words since it is about dragons.",
            "This is the fourth paragraph, it contains the word Elderberry capitalised."));
    

    @Test
    void testFindParagraphsContainingTheseWords_findsSingleParaContainingSingleWord() {
        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(DUMMY_INPUT_DATA, Collections.singletonList("banana"));
        assertEquals(output.size(), 1);
        assertTrue(output.get(0).contains("second paragraph"));
    }

    @Test
    void testFindParagraphsContainingTheseWords_findsTwoParasContainingSingleWord() {

        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(DUMMY_INPUT_DATA, Collections.singletonList("apple"));
        assertEquals(output.size(), 2);
        assertTrue(output.get(0).contains("first paragraph"));
        assertTrue(output.get(1).contains("second paragraph"));
    }

    @Test
    void testFindParagraphsContainingTheseWords_findsSingleParaContainingTwoWordsButDoesNotDuplicate() {

        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(DUMMY_INPUT_DATA, Arrays.asList(new String[]{"no", "dragons"}));
        assertEquals(output.size(), 1);
        assertTrue(output.get(0).contains("third paragraph"));
    }

    @Test
    void testFindParagraphsContainingTheseWords_isCaseInsensitive() {
        List<String> output = summariser.findParagraphsContainingAnyOfTheseWords(DUMMY_INPUT_DATA, Collections.singletonList("elderberry"));
        assertEquals(output.size(), 1);
        assertTrue(output.get(0).contains("fourth paragraph"));
    }

    @Test
    void testGenerateSummarySection_hasCorrectParagraphs(){
        ContractSummaryConfig.Section mockSection = new ContractSummaryConfig.Section("", Collections.singletonList("apple"));
        ContractSummarySection result = summariser.generateSummarySection(DUMMY_INPUT_DATA, mockSection);
        assertEquals(result.getResultsParagraphs().size(), 2);
    }

    @Test
    void testGenerateSummarySection_hasTitle(){
        ContractSummaryConfig.Section mockSection = new ContractSummaryConfig.Section("Mock Title", Collections.emptyList());
        ContractSummarySection result = summariser.generateSummarySection(DUMMY_INPUT_DATA, mockSection);
        assertEquals(result.getTitle(), "Mock Title");
    }

}
