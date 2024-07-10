package com.example.fsoft.employee_manager.service;

import com.example.fsoft.employee_manager.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(String id);
    Employee addEmployee(Employee employee);
    Employee updateEmployee(Employee employee);
    void deleteEmployee(String id);
    void saveAllEmployees(List<Employee> employees);
}
