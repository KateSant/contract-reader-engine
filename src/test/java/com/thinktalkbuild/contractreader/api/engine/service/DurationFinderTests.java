package com.thinktalkbuild.contractreader.api.engine.service;

import com.thinktalkbuild.contractreader.api.engine.model.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Period;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class DurationFinderTests {

    @Autowired
    private DurationFinder durationfinder = new DurationFinder(new Highlighter());

    String DURATION_PARA_11Months = "This Agreement shall continue for a period of 11 months from the Services Start Date (the “Initial Term”). ";
    Period DURATION_FOUND_11Months = Period.ofMonths(11);

    String DURATION_PARA_1Month = "This Agreement shall continue for a period of 1 month. ";
    Period DURATION_FOUND_1Month = Period.ofMonths(1);

    String DURATION_PARA_500Years = "for a period of 500 years ";
    Period DURATION_FOUND_500Years = Period.ofYears(500);

    String DURATION_PARA_2YearsUpperAndLower = "for a perioD of 2 YeArs ";
    Period DURATION_FOUND_2Years = Period.ofYears(2);

    String DURATION_PARA_Multiples = "there is a period of 1 month and a period of 2 years";


    @Test
    void testFindDuration_findsMonths() {
        List<Duration> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_11Months);
        assertEquals(durations.get(0).getPeriod(), DURATION_FOUND_11Months);
    }

    @Test
    void testFindDuration_findsSingleMonth() {
        List<Duration> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_1Month);
        assertEquals(durations.get(0).getPeriod(), DURATION_FOUND_1Month);
    }

    @Test
    void testFindDuration_findsYears() {
        List<Duration> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_500Years);
        assertEquals(durations.get(0).getPeriod(), DURATION_FOUND_500Years);
    }

    @Test
    void testFindDuration_isCaseInsensitive() {
        List<Duration> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_2YearsUpperAndLower);
        assertEquals(durations.get(0).getPeriod(), DURATION_FOUND_2Years);
    }

    @Test
    void testFindDuration_findsMultiples() {
        List<Duration> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_Multiples);
        assertEquals(durations.get(0).getPeriod(), DURATION_FOUND_1Month);
        assertEquals(durations.get(1).getPeriod(), DURATION_FOUND_2Years);
    }

    @Test
    void testFindDuration_findsWholeChunk() {
        List<Duration> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_11Months);
        assertEquals(durations.get(0).getContext(), DURATION_PARA_11Months);

         durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_1Month);
        assertEquals(durations.get(0).getContext(), DURATION_PARA_1Month);
    }

    @Test
    void testFindDuration_doesHighlighting() {
        List<Duration> durations =  durationfinder.findDurationsInParagraph(DURATION_PARA_11Months);
        assertTrue(durations.get(0).getContextHighlighted().contains("<span class=\"highlight\">11</span>"));
    }


}
