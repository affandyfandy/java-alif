package com.example.employee_manager.controller;

import com.example.employee_manager.dao.EmployeeDAO;
import com.example.employee_manager.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    // Create an Employee by sending a POST request to /api/employees with the Employee object in the request body
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employeeDAO.save(employee);
        return ResponseEntity.ok(employee);
    }

    // Get all Employees by sending a GET request to /api/employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    // Get an Employee by ID by sending a GET request to /api/employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeDAO.findById(id));
    }

    // Update an Employee by sending a PUT request to /api/employees/{id} with the updated Employee object in the request body
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employeeDetails) {
        Employee employee = employeeDAO.findById(id);
        employee.setName(employeeDetails.getName());
        employee.setAddress(employeeDetails.getAddress());
        employee.setDepartment(employeeDetails.getDepartment());
        employeeDAO.update(employee);
        return ResponseEntity.ok(employee);
    }

    // Delete an Employee by sending a DELETE request to /api/employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}
