package com.thinktalkbuild.contractreader.api.engine.service;

import com.thinktalkbuild.contractreader.api.engine.model.Obligation;
import com.thinktalkbuild.contractreader.api.engine.model.ObligationsByParty;
import com.thinktalkbuild.contractreader.api.engine.model.config.ObligationsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kate
 */
@Service
public class ObligationsFinder {


    public static String PARTY = "[^\\s]+"; // The party.   A word.  (Any character other than space, 1 or more times.)
    public static String SPACE = " ";
    public static String OBLIGING_VERBS_PLACEHOLDER="OBLIGING_VERBS_PLACEHOLDER"; // this will be substituted at runtime because it comes from yaml config.
    public static String regex = ".*?" // The beginning of the sentence (zero or more any character, non greedy match)
                                + "("+PARTY+")"
                                + SPACE
                                + "("+OBLIGING_VERBS_PLACEHOLDER+")" // (e.g. must|shall)
                                + SPACE
                                +"(.+?)" // The action the party must do.  Possibly.  (zero or more any character, non-greedy match), up to...
                                +"(\\.|(?="+PARTY+SPACE+"("+OBLIGING_VERBS_PLACEHOLDER+")))"; // The end of the sentence (full stop)  OR another party+space+obliging verb - NB lookahead,don't consume it, since we want it in the next match.




    private ObligationsConfig obligationsConfig;
    private Highlighter highlighter;
    private Pattern pattern;

    @Autowired
    public ObligationsFinder(Highlighter highlighter, ObligationsConfig obligationsConfig){
        this.obligationsConfig=obligationsConfig;
        this.highlighter=highlighter;
        String obligingVerbsJoined = String.join("|", obligationsConfig.getObligingVerbs());
        String regexWithVerbs = regex.replaceAll(OBLIGING_VERBS_PLACEHOLDER,obligingVerbsJoined);
        pattern = Pattern.compile(regexWithVerbs);
    }

    public ObligationsByParty findAndSortObligations(List<String> inputParagraphs) {
        List<Obligation> rawObligations = findObligationsInDocument(inputParagraphs);
        Map<String, List<Obligation>> sortedObligations = sortOblgationsByParty(rawObligations);
        filterOutNonParties(sortedObligations);
        return new ObligationsByParty(sortedObligations);
    }



    protected List<Obligation> findObligationsInDocument(List<String> inputParagraphs) {
        List<Obligation> obligations = new ArrayList<>();
        inputParagraphs.stream().forEach(para -> {
            obligations.addAll(findObligationsInParagraph(para));
        });
        return obligations;
    }

    protected List<Obligation> findObligationsInParagraph(String inputParagraph) {
        List<Obligation> obligations = new ArrayList<>();
        Matcher matcher = pattern.matcher(inputParagraph);
        while (matcher.find()) {
            Obligation obl = new Obligation();
            obl.setContext(matcher.group(0));
            obl.setParty(matcher.group(1));
            obl.setObligingVerb(matcher.group(2));
            obl.setAction(matcher.group(3));
            String highlighted = highlighter.highlight(obl.getContext(), List.of(
                    obl.getObligingVerb(),
                    obl.getAction()
                    ));
            obl.setContextHighlighted(highlighted);
            obligations.add(obl);
        }

        return obligations;
    }


    protected void filterOutNonParties(Map<String, List<Obligation>> sortedObligations) {

        for(String nonParty : obligationsConfig.getNonParties()){
            String nonPartyKey = nonParty.toUpperCase();
            if(sortedObligations.containsKey(nonPartyKey)){
                sortedObligations.remove(nonPartyKey);
            }
        }
    }

    protected Map<String, List<Obligation>> sortOblgationsByParty(List<Obligation> rawObligations){
        Map<String, List<Obligation>> sortedObligations = new HashMap<>();
        for(Obligation obl: rawObligations){
            String party = obl.getParty().toUpperCase();
            if(sortedObligations.containsKey(party)){
                sortedObligations.get(party).add(obl);// add one to list
            }else{
                sortedObligations.put(party, new ArrayList<>(List.of(obl))); // start new list
            }
        }
        return sortedObligations;
    }
}