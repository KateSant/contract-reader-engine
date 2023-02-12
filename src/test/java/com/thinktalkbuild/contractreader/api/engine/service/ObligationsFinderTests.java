package com.thinktalkbuild.contractreader.api.engine.service;


import com.thinktalkbuild.contractreader.api.engine.model.Obligation;
import com.thinktalkbuild.contractreader.api.engine.model.ObligationsByParty;
import com.thinktalkbuild.contractreader.api.engine.model.config.ObligationsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author kate
 */
public class ObligationsFinderTests {

    private ObligationsFinder obligationsFinder;

    @BeforeEach
    void setup(){
        ObligationsConfig mockConfig = new ObligationsConfig();
        mockConfig.setObligingVerbs(List.of("shall", "must"));
        mockConfig.setNonParties(List.of("agreement", "addendum"));
        obligationsFinder = new ObligationsFinder(new Highlighter(), mockConfig);
    }

    static String PARAGRAPH_WITH_3_OBLIGATIONS = "This is the first sentence, it says that The Supplier must sit down.  This is the second sentence, it says that a Supplier must also stand up.  " +
            "This is the third sentence in the paragraph, it says that The Customer shall do a hand stand.";

    static List<String> LIST_OF_PARAGRAPHS = new ArrayList<>(List.of(
            PARAGRAPH_WITH_3_OBLIGATIONS,
            "This is the second paragraph, it says that The Customer shall stand up."));

    static String SENTENCE_WITH_2_OBLGATIONS="The Customer must pay the money and the Supplier must provide the service.";

    static String SENTENCE_WITH_NON_PARTIES="This Agreement shall remain in force for 2 years. The Supplier shall do something.";

    @Test
    void testFindObligations_finds_must() {

        List<Obligation> obligations =  obligationsFinder.findObligationsInParagraph(PARAGRAPH_WITH_3_OBLIGATIONS);
        assertTrue(obligations.size()>0);
        Obligation firstSentence = obligations.get(0);
        assertEquals(firstSentence.getContext(), "This is the first sentence, it says that The Supplier must sit down.");
        assertEquals(firstSentence.getObligingVerb(), "must");
        assertEquals(firstSentence.getParty(), "Supplier");
        assertEquals(firstSentence.getAction(), "sit down");
    }

    @Test
    void testFindObligations_finds_shall() {
        List<Obligation> obligations =  obligationsFinder.findObligationsInParagraph(PARAGRAPH_WITH_3_OBLIGATIONS);
        assertTrue(obligations.size()>0);
        assertTrue(obligations.stream().anyMatch(obl -> obl.getObligingVerb().equals("shall")));
    }

    @Test
    void testFindObligations_finds_multiple_obligaitons_in_para() {
        List<Obligation> obligations =  obligationsFinder.findObligationsInParagraph(PARAGRAPH_WITH_3_OBLIGATIONS);
        assertEquals(obligations.size(),3);
    }

    @Test
    void testFindObligations_finds_multiple_obligations_in_sentence() {
        List<Obligation> obligations =  obligationsFinder.findObligationsInParagraph(SENTENCE_WITH_2_OBLGATIONS);
        assertEquals(obligations.size(),2);
        assertTrue(obligations.get(0).getAction().contains("pay the money"));
        assertTrue(obligations.get(1).getAction().contains("provide the service"));
    }


    @Test
    void testSortsObligationsByParty() {
        ObligationsByParty obligationsByParty = obligationsFinder.findAndSortObligations(LIST_OF_PARAGRAPHS);
        assertEquals(obligationsByParty.getSortedObligations().get("SUPPLIER").size(), 2);
        assertEquals(obligationsByParty.getSortedObligations().get("CUSTOMER").size(), 2);
    }

    @Test
    void testFilterObligationsFiltersOutNonParties(){
        List<Obligation> rawObligations =  obligationsFinder.findObligationsInParagraph(SENTENCE_WITH_NON_PARTIES);
        Map<String, List<Obligation>> sortedObligations = obligationsFinder.sortOblgationsByParty(rawObligations);
        assertEquals(2, sortedObligations.keySet().size());
        obligationsFinder.filterOutNonParties(sortedObligations);
        assertEquals(1, sortedObligations.keySet().size());
    }

    @Test
    void testFindAnSortFiltersOutNonParties(){

        List<Obligation> rawObligations =  obligationsFinder.findObligationsInParagraph(SENTENCE_WITH_NON_PARTIES);
        assertEquals(2, rawObligations.size());

        ObligationsByParty obligationsByParty = obligationsFinder.findAndSortObligations(Collections.singletonList(SENTENCE_WITH_NON_PARTIES));
        assertEquals(1, obligationsByParty.getSortedObligations().keySet().size());
        assertNotNull(obligationsByParty.getSortedObligations().get("SUPPLIER"));
        assertNull(obligationsByParty.getSortedObligations().get("AGREEMENT"));// should be filtered
    }


}
