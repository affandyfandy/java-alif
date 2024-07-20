package com.example.assignment_code.controller;

import com.example.assignment_code.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/notify")
    public String notifyEmployee() {
        String receiver = "john@example.com";
        String workDetails = "Here is details of your work";
        employeeService.notifyEmployee(receiver, workDetails);
        return "Notification sent to " + receiver;
    }
}
