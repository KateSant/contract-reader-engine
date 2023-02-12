package com.thinktalkbuild.contractreader.api.engine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 *
 * @author kate
 */
@Service
@Slf4j
public class Highlighter {

    public static String PRE_SPAN = "<span class=\"highlight\">";
    public static String POST_SPAN = "</span>";

    public  List<String> highlight(List<String> inputParagraphs, List<String> words) {
        List<String> outputParagraphs = new ArrayList<>();

        inputParagraphs.stream().forEach(para -> {
            outputParagraphs.add(highlight(para, words));
        });

        return outputParagraphs;

    }

    protected String highlight(String paragraph, List<String> words) {
        String outputParagraph = paragraph;
        for(String word: words){
            outputParagraph = outputParagraph.replace(word, PRE_SPAN+word+POST_SPAN);
        }
        return outputParagraph;
    }
}