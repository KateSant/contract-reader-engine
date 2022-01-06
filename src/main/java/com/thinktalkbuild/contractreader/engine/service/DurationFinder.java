package com.thinktalkbuild.contractreader.engine.service;

import com.thinktalkbuild.contractreader.engine.model.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class DurationFinder {

    public static String regex = "a period of " +
                                    "(\\d+) " + //digits
                                    "(month|year)(s\\b|\\b)"; //optional "s" then end of word


    private Pattern pattern;
    private Highlighter highlighter;


    public DurationFinder(Highlighter highlighter){
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        this.highlighter=highlighter;
    }

    public List<Duration> findDurationsInDocument(List<String> inputParagraphs) {
        List<Duration> durations = new ArrayList<>();
        inputParagraphs.stream().forEach(para -> {
            durations.addAll(findDurationsInParagraph(para));
        });
        return durations;
    }

    protected List<Duration> findDurationsInParagraph(String para) {

        List<Duration> durations = new ArrayList<>();
        Matcher matcher = pattern.matcher(para);
        while (matcher.find()) {

            String howManyFound = matcher.group(1);
            int howMany = Integer.parseInt(matcher.group(1));
            String monthsOrYears = matcher.group(2).toLowerCase();
            String wholeChunk= matcher.group(0).toLowerCase();

            log.debug("Found duration : howmany= {}, monthsoryears= {}", howMany, monthsOrYears);
            Duration duration = new Duration();
            duration.setContext(para);

            if(monthsOrYears.contains("month")){
                duration.setPeriod(Period.ofMonths(howMany));
            }else if(monthsOrYears.contains("year")){
                duration.setPeriod(Period.ofYears(howMany));
            }

            String highlighted = highlighter.highlight(para, List.of(
                    howManyFound,
                    monthsOrYears
                ));
            duration.setContextHighlighted(highlighted);

            durations.add(duration);
        }
        return durations;
    }
}