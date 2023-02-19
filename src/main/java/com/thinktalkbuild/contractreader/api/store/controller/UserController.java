package com.thinktalkbuild.contractreader.api.store.controller;

import com.thinktalkbuild.contractreader.api.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import java.util.concurrent.ExecutionException;
import com.thinktalkbuild.contractreader.api.store.service.UserService;

/**
 *
 * @author kate
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public void user() throws Exception {

        log.info("Call to /user POST endpoint");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        log.info("Authentication username = [{}]", username);
        log.info("Authentication principal", authentication.getPrincipal());
        Jwt principal = (Jwt)authentication.getPrincipal();

        log.info("Jwt subject = [{}]", principal.getSubject());
        log.info("Jwt issuer = [{}]", principal.getIssuer());

        userService.addUser(principal.getSubject());

    }
}
