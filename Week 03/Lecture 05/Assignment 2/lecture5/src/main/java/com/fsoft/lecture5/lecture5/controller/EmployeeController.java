package com.fsoft.lecture5.lecture5.controller;

import com.fsoft.lecture5.lecture5.model.Employee;
import com.fsoft.lecture5.lecture5.repository.EmployeeRepository;
import com.fsoft.lecture5.lecture5.utils.FileUtils;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private FileUtils fileUtils;

    @GetMapping
    public ResponseEntity<List<Employee>> listAllEmployees() {
        List<Employee> listEmployees = employeeRepository.findAll();
        if (listEmployees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEmployees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployee(@PathVariable("id") String id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            return ResponseEntity.ok(employeeOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employee.getId());
        if (employeeOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") String id,
                                                   @RequestBody Employee employeeDetails) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setName(employeeDetails.getName());
            employee.setDateOfBirth(employeeDetails.getDateOfBirth());
            employee.setAddress(employeeDetails.getAddress());
            employee.setDepartment(employeeDetails.getDepartment());

            Employee updatedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") String id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            employeeRepository.delete(employeeOpt.get());
            return ResponseEntity.ok("Employee deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/import")
    public ResponseEntity<String> importEmployeesFromCsv(@RequestParam("file") MultipartFile file) {
        try {
            fileUtils.importData(file);
            return ResponseEntity.ok("File imported successfully.");
        } catch (IOException | CsvValidationException e) {
            return ResponseEntity.status(500).body("Error importing file: " + e.getMessage());
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable("department") String department){
        List<Employee> employees = employeeRepository.findByDepartment(department);
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employees);
    }
}
