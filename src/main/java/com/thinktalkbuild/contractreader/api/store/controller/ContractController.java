package com.thinktalkbuild.contractreader.api.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinktalkbuild.contractreader.api.store.model.ContractMetadata;
import com.thinktalkbuild.contractreader.api.store.service.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author kate
 */
@RestController
@Slf4j
public class ContractController extends JwtSecureController{

    @Autowired
    private ContractService contractService;

    @PostMapping(value = "/contract", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ContractMetadata contract(@RequestBody ContractMetadata contract) throws Exception {
        log.info("Call to /contract POST endpoint with body: {}", contract);
        Jwt jwtPrincipal = getSecurityPrincipal();
        ContractMetadata insertedContract = contractService.insertContractForUser(contract, jwtPrincipal.getSubject());
        log.info("Inserted contract: {}", new ObjectMapper().writeValueAsString(insertedContract));
        return insertedContract;
    }

    @GetMapping(value = "/contract")
    public List<ContractMetadata> getContracts() throws Exception {
        log.info("Call to /contract GET endpoint");
        Jwt jwtPrincipal = getSecurityPrincipal();
        return contractService.getContractsForUser(jwtPrincipal.getSubject());
    }
}
