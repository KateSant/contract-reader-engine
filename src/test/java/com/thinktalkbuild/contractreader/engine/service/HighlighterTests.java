package com.thinktalkbuild.contractreader.engine.service;



import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author kate
 */
public class HighlighterTests {


    private Highlighter highlighter = new Highlighter();


    public static String DUMMY_TEXT = "This text mentions apples and bananas.";

    @Test
    void testHighlight_handlesSingleWord(){
        String output = highlighter.highlight(DUMMY_TEXT, Collections.singletonList("apples"));
        assertEquals(output,"This text mentions <span class=\"highlight\">apples</span> and bananas.");
    }
    @Test
    void testHighlight_handlesTwoWords(){
        String output = highlighter.highlight(DUMMY_TEXT, List.of("apples", "bananas"));
        assertEquals(output,"This text mentions <span class=\"highlight\">apples</span> and <span class=\"highlight\">bananas</span>.");
    }
    @Test
    void testHighlight_handlesOverlappingWords(){
        String output = highlighter.highlight(DUMMY_TEXT, List.of("apples", "bananas"));
        assertEquals(output,"This text mentions <span class=\"highlight\">apples</span> and <span class=\"highlight\">bananas</span>.");
    }

    @Test
    void testHighlight_handlesSpecialCharactersInHighlightedText(){
        String brackets = highlighter.highlight("abc (de fg", List.of("(de"));
        assertEquals(brackets, "abc <span class=\"highlight\">(de</span> fg");

        String dollar = highlighter.highlight("abc $5,000,000 def", List.of("$5,000,000"));
        assertEquals(dollar, "abc <span class=\"highlight\">$5,000,000</span> def");
    }



}
