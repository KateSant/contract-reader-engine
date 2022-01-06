package com.thinktalkbuild.contractreader.engine.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kate
 */
@Service
public class WordDocReader {

    public String extractTextFromFile(MultipartFile file) throws IOException, InvalidFormatException {
        XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(file.getInputStream()));
        XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
        return extractor.getText();
    }
    
    public List<String> parseParagraphs(String raw){
        List<String> paragraphs = new ArrayList<>();      
        String[] split = raw.split("\\n");   
        for(String line: split){
            if (!isEmptyOrWhitespace(line)){
                paragraphs.add(line);
            
            }     
        }
        return paragraphs;
    }
    
    private boolean isEmptyOrWhitespace(String line){
        return line.isEmpty() || line.matches("^\\s*$");
    }

}
