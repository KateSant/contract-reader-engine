package com.thinktalkbuild.contractreader.engine.model.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "contract-summary")
public class ContractSummaryConfig {

    private List<Section> sections;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public static class Section {

        private String title;

        private List<String> searchWords;

        public Section(String title, List<String> searchWords) {
            this.title = title;
            this.searchWords = searchWords;
        }
        public Section() { }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public List<String> getSearchWords() {
            return searchWords;
        }

        public void setSearchWords(List<String> searchWords) {
            this.searchWords = searchWords;
        }
    }
}