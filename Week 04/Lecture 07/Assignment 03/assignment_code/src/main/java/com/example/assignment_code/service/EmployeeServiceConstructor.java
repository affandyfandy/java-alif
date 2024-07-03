package com.example.assignment_code.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class EmployeeServiceConstructor {
    private final EmailService emailService;

    @Autowired
    public EmployeeServiceConstructor(@Qualifier("primaryEmailService") EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyEmployee(String receiver, String workDetails) {
        emailService.sendEmail(receiver, "(Constructor) Work Notification", workDetails);
    }
}
