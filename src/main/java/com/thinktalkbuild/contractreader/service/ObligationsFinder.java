package com.thinktalkbuild.contractreader.service;

import com.thinktalkbuild.contractreader.model.ContractSummary;
import com.thinktalkbuild.contractreader.model.ContractSummarySection;
import com.thinktalkbuild.contractreader.model.Obligation;
import com.thinktalkbuild.contractreader.model.config.ContractSummaryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author kate
 */
@Service
public class ObligationsFinder {

    public static List<String> OBLIGING_VERBS = new ArrayList<>(List.of("shall", "must"));
    public static String regex = ".*?" // The beginning of the sentence (zero or more any character, non greedy match)
                                + "([^\\s]+)" // The party.   A word.  (Any character other than space, 1 or more times.)
                                + " " // A space
                                + "(%s)" // must or shall (inserted later)
                                + " " // A space
                                +".+?" // The end of the sentence (zero or more any character, non-greedy match)
                                +"\\."; // A full stop



    protected List<Obligation> findObligations(List<String> inputParagraphs) {
        List<Obligation> obligations = new ArrayList<>();
        inputParagraphs.stream().forEach(para -> {
            obligations.addAll(findObligations(para));
        });
        return obligations;
    }

    protected List<Obligation> findObligations(String inputParagraph) {
        List<Obligation> obligations = new ArrayList<>();
        String regexWithVerb = String.format(regex, "must");
        Pattern pattern = Pattern.compile(regexWithVerb, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputParagraph);
        while (matcher.find()) {
            System.out.println("0="+ matcher.group(0)+" 1="+matcher.group(1)+" 2="+matcher.group(2));
            Obligation obl = new Obligation();
            obl.setWholeSentence(matcher.group(0));
            obl.setParty(matcher.group(1));
            obl.setObligingVerb(matcher.group(2));
            obligations.add(obl);
        }



        return obligations;
    }



}