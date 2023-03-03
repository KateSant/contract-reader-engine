package com.thinktalkbuild.contractreader.api.store.controller;

import com.thinktalkbuild.contractreader.api.store.model.ContractMetadata;
import com.thinktalkbuild.contractreader.api.store.model.User;
import com.thinktalkbuild.contractreader.api.store.repo.ContractRepo;
import com.thinktalkbuild.contractreader.api.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kate
 */
@RestController
@Slf4j
public class ContractController extends JwtSecureController{

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/contract", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void contract(@RequestBody ContractMetadata contract) throws Exception {

        log.info("Call to /contract POST endpoint with body: {}", contract);
        Jwt jwtPrincipal = getSecurityPrincipal();
        log.info("Security principal = {}", jwtPrincipal.getSubject());

        User user = userService.insertUserIfNotExists(jwtPrincipal.getSubject());
        log.info("Inserted or found user: {}", user);

        contractRepo.insertContract(contract, user);
    }
}
