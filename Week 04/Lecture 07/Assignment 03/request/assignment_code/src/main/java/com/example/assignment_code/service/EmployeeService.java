package com.example.assignment_code.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmployeeService {
    private final EmailService emailService;

    @Autowired
    public EmployeeService(@Qualifier("primaryEmailService") EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyEmployee(String receiver, String workDetails) {
        emailService.sendEmail(receiver, "(Constructor) Work Notification", workDetails);
    }

    @PostConstruct
    public void init() {
        System.out.println("EmployeeService instance created: " + this);
    }
}
