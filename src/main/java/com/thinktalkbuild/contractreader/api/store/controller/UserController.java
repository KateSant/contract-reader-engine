package com.thinktalkbuild.contractreader.api.store.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 *
 * @author kate
 */
@RestController
@Slf4j
public class UserController {


    @PostMapping("/user")
    public void user(){

        log.info("Call to /user POST endpoint");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        Jwt principal = (Jwt)authentication.getPrincipal();

        log.info("Principal subject = [{}]", principal.getSubject());

    }
}
