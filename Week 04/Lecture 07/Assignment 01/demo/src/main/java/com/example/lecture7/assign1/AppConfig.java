package com.example.lecture7.assign1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    // Setter based dependency injection
    @Bean
    public Employee employee() {
        Employee employee = new Employee(1,"GL", 25);
        employee.setEmployeeWork(employeeWork());
        return employee;
    }

    // Field based dependency injection
//    @Bean
//    public Employee employee() {
//        return new Employee(1,"GL", 25);
//    }
}

