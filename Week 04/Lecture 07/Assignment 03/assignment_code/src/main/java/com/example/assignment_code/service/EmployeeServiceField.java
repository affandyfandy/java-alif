package com.example.assignment_code.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class EmployeeServiceField {
    @Autowired
    @Qualifier("primaryEmailService")
    private EmailService emailService;

    public void notifyEmployee(String receiver, String workDetails) {
        emailService.sendEmail(receiver, "(Field) Work Notification", workDetails);
    }
}
