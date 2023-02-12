package com.thinktalkbuild.contractreader.api.engine.service;

import java.io.IOException;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;



/**
 *
 * @author kate
 */
public class WordDocReaderTests {
    
    
   private WordDocReader reader = new WordDocReader();
   private TestUtils utils = new TestUtils();

   @Test
   void testExtractTextFromWordDoc() throws IOException, InvalidFormatException{
       
       MockMultipartFile mockFile = utils.dummyWordDoc("WordDocWithLinesAndParagraphs.docx");
       String text = reader.extractTextFromFile(mockFile);
       assertFalse(text.isEmpty());
       
   }
   
    @Test
   void testParseParagraphs_ignoresWitespaceLines() throws IOException, InvalidFormatException{
       
       MockMultipartFile mockFile = utils.dummyWordDoc("WordDocWithLinesAndParagraphs.docx");
       String raw = reader.extractTextFromFile(mockFile);
       List<String> paragraphs = reader.parseParagraphs(raw);
       assertEquals(paragraphs.size(), 3);  // file has 3 text lines and 2 whitespace lines
       
   }
   


    
}
