package com.thinktalkbuild.contractreader.controller;

import com.thinktalkbuild.contractreader.model.*;
import com.thinktalkbuild.contractreader.service.ContractSummariser;
import com.thinktalkbuild.contractreader.service.DurationFinder;
import com.thinktalkbuild.contractreader.service.ObligationsFinder;
import com.thinktalkbuild.contractreader.service.WordDocReader;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kate
 */
@RestController
@Slf4j
public class AnalyseController {

    private WordDocReader reader;

    private ContractSummariser summariser;

    private ObligationsFinder obligationsFinder;

    private DurationFinder durationFinder;

    @Autowired
    public AnalyseController(WordDocReader reader, ContractSummariser summariser, ObligationsFinder obligationsFinder, DurationFinder durationFinder){
        this.reader=reader;
        this.summariser=summariser;
        this.obligationsFinder=obligationsFinder;
        this.durationFinder = durationFinder;
    }
    


    @PostMapping("/analyse")
    public Analysis analyse(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {

            Analysis analysis = new Analysis();

            String text = reader.extractTextFromFile(file);
            List<String> paragraphs = reader.parseParagraphs(text);

            analysis.setFileName(file.getOriginalFilename());

            ContractSummary summary = summariser.summarise(paragraphs);
            analysis.setSummary(summary);

            ObligationsByParty obligationsByParty = obligationsFinder.findAndSortObligations(paragraphs);
            analysis.setObligationsByParty(obligationsByParty);
            List<Duration> durations = durationFinder.findDurationsInDocument(paragraphs);
            analysis.setDurations(durations);

        return analysis;

    }
}
