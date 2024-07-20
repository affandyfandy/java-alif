package com.example.assignment_code.service.impl;

import com.example.assignment_code.service.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Qualifier("primaryEmailService")
public class EmailServiceImpl implements EmailService {
    // Send an email
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
