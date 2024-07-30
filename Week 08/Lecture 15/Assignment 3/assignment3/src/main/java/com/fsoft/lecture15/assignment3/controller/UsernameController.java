package com.fsoft.lecture15.assignment3.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usernames")
public class UsernameController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping
    public ResponseEntity<String> getAndPrintUsername(HttpServletResponse response) {
        String username = (String) response.getHeader("Username");
        logger.info("Username: {}", username);
        return ResponseEntity.ok("Username: " + username);
    }
}
