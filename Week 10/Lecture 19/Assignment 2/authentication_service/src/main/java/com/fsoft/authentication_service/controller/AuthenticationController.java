package com.fsoft.authentication_service.controller;

import com.fsoft.authentication_service.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/auth")
    public ResponseEntity<String> validateApiKey(@RequestHeader("API-KEY") String apiKey) {
        if (authenticationService.isValidApiKey(apiKey)) {
            return ResponseEntity.ok("Valid API key");
        } else {
            return ResponseEntity.status(401).body("Invalid API key");
        }
    }
}
