package com.thinktalkbuild.contractreader.api.store.controller;

import com.thinktalkbuild.contractreader.api.store.model.User;
import com.thinktalkbuild.contractreader.api.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kate
 */
@RestController
@Slf4j
public class ContractController extends JwtSecureController{


    @PostMapping("/contract")
    public void contract() throws Exception {

        log.info("Call to /contract POST endpoint");
        Jwt jwtPrincipal = getSecurityPrincipal();
        log.info("Security principal = {}", jwtPrincipal.getSubject());
    }
}
