package com.example.assignment_code.config;

import com.example.assignment_code.service.EmailService;
import com.example.assignment_code.service.EmployeeServiceConstructor;
import com.example.assignment_code.service.EmployeeServiceField;
import com.example.assignment_code.service.EmployeeServiceSetter;
import com.example.assignment_code.service.impl.EmailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.assignment_code")
public class AppConfig { }
