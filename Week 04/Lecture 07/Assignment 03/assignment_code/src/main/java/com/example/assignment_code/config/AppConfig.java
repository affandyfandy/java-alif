package com.example.assignment_code.config;

import com.example.assignment_code.service.EmailService;
import com.example.assignment_code.service.EmployeeServiceConstructor;
import com.example.assignment_code.service.EmployeeServiceField;
import com.example.assignment_code.service.EmployeeServiceSetter;
import com.example.assignment_code.service.impl.EmailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.example.assignment_code")
public class AppConfig {

    // Define bean for EmailService
    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl();
    }

    // Define singleton bean for EmployeeServiceConstructor
    @Bean
    @Scope("singleton")
    public EmployeeServiceConstructor employeeServiceConstructor() {
        return new EmployeeServiceConstructor( emailService() );
    }

    // Define prototype bean for EmployeeServiceField
    @Bean
    @Scope("prototype")
    public EmployeeServiceField employeeServiceField() {
        return new EmployeeServiceField();
    }
}
