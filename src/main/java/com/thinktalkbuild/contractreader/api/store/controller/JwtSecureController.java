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

@Slf4j
public class JwtSecureController {

    protected Jwt getSecurityPrincipal(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        log.info("Authentication username = [{}]", username);
        log.info("Authentication principal", authentication.getPrincipal());
        Jwt principal = (Jwt)authentication.getPrincipal();

        log.info("Jwt subject = [{}]", principal.getSubject());
        log.info("Jwt issuer = [{}]", principal.getIssuer());
        return principal;
    }
}
