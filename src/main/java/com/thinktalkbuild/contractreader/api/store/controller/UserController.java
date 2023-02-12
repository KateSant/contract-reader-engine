package com.thinktalkbuild.contractreader.api.store.controller;

import com.thinktalkbuild.contractreader.api.engine.model.Analysis;
import com.thinktalkbuild.contractreader.api.engine.model.ContractSummary;
import com.thinktalkbuild.contractreader.api.engine.model.Duration;
import com.thinktalkbuild.contractreader.api.engine.model.ObligationsByParty;
import com.thinktalkbuild.contractreader.api.engine.service.ContractSummariser;
import com.thinktalkbuild.contractreader.api.engine.service.DurationFinder;
import com.thinktalkbuild.contractreader.api.engine.service.ObligationsFinder;
import com.thinktalkbuild.contractreader.api.engine.service.WordDocReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author kate
 */
@RestController
@Slf4j
public class UserController {


    @PostMapping("/user")
    public void user(){


    }
}
