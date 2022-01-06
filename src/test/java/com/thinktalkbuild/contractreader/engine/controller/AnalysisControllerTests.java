package com.thinktalkbuild.contractreader.engine.controller;

import com.thinktalkbuild.contractreader.engine.model.*;
import com.thinktalkbuild.contractreader.engine.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.Period;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnalysisControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WordDocReader reader;

    @MockBean
    private ContractSummariser summariser;

    @MockBean
    private ObligationsFinder obligationsFinder;

    @MockBean
    private DurationFinder durationFinder;


    @Test
    void whenPostAFile_thenReceiveAnalysis() throws Exception {
        MockMultipartFile mockFile = new TestUtils().dummyWordDoc("WordDocWithLinesAndParagraphs.docx");
        ContractSummary mockContractSummary = new ContractSummary();
        mockContractSummary.addSection(new ContractSummarySection("Dummy section title", Collections.singletonList("Dummy section")));

        Obligation mockObligation = new Obligation();
        mockObligation.setParty("The Supplier");
        mockObligation.setContextHighlighted("The Supplier must do stuff");
        Map<String, List<Obligation>> mockSortedObligations = new HashMap<>();
        mockSortedObligations.put("SUPPLIER", Collections.singletonList(mockObligation));
        ObligationsByParty obligations = new ObligationsByParty(mockSortedObligations);

        Duration duration = new Duration();
        duration.setPeriod(Period.ofMonths(42));
        List<Duration> mockDurations = Collections.singletonList(duration);

        when(reader.extractTextFromFile(mockFile)).thenReturn("Dummy raw text");
        when(reader.parseParagraphs(anyString())).thenReturn(Collections.singletonList("Dummy raw paragraph"));
        when(summariser.summarise(anyList())).thenReturn(mockContractSummary);
        when(obligationsFinder.findAndSortObligations(anyList())).thenReturn(obligations);
        when(durationFinder.findDurationsInDocument(anyList())).thenReturn(mockDurations);

        mvc.perform(multipart("/analyse")
                .file(mockFile))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.fileName", is("WordDocWithLinesAndParagraphs.docx")))
                .andExpect(jsonPath("$.summary.sections", hasSize(1)))
                .andExpect(jsonPath("$.summary.sections[0].title", is("Dummy section title")));
    }
}
