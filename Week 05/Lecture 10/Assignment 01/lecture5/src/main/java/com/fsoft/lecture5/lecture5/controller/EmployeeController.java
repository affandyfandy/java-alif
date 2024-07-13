package com.fsoft.lecture5.lecture5.controller;

import com.fsoft.lecture5.lecture5.dto.EmployeeDTO;
import com.fsoft.lecture5.lecture5.exception.EmployeeAlreadyExistsException;
import com.fsoft.lecture5.lecture5.exception.NoSuchEmployeeExistsException;
import com.fsoft.lecture5.lecture5.mapper.EmployeeMapper;
import com.fsoft.lecture5.lecture5.model.Employee;
import com.fsoft.lecture5.lecture5.repository.EmployeeRepository;
import com.fsoft.lecture5.lecture5.service.EmployeeService;
import com.fsoft.lecture5.lecture5.utils.FileUtils;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.validation.Valid;
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
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final FileUtils fileUtils;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, FileUtils fileUtils) {
        this.employeeService = employeeService;
        this.fileUtils = fileUtils;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO employee = employeeService.createEmployee(employeeDTO);
            return ResponseEntity.ok(employee);
        } catch (EmployeeAlreadyExistsException e) {
            return ResponseEntity.status(409).body(new EmployeeDTO());
        }
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") UUID id) {
        try {
            EmployeeDTO employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (NoSuchEmployeeExistsException e) {
            return ResponseEntity.status(404).body(new EmployeeDTO());
        }
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable("department") String department) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment(department);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") UUID id, @RequestBody @Valid EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO employee = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(employee);
        } catch (NoSuchEmployeeExistsException e) {
            return ResponseEntity.status(404).body(new EmployeeDTO());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") UUID id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully.");
        } catch (NoSuchEmployeeExistsException e) {
            return ResponseEntity.status(404).body("Employee not found.");
        }
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
}
