package com.thinktalkbuild.contractreader.api.engine.service;

import org.springframework.mock.web.MockMultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TestUtils {

    public MockMultipartFile dummyWordDoc(String fileName) throws FileNotFoundException, IOException {

        InputStream dataStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        return new MockMultipartFile("file", fileName, "multipart/form-data", dataStream);

    }
}
