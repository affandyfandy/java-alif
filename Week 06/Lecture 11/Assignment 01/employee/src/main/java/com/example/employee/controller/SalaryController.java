package com.example.employee.controller;

import com.example.employee.dto.SalaryDTO;
import com.example.employee.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/salaries")
public class SalaryController {
    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<SalaryDTO> addSalary(@PathVariable("id") Integer id, @RequestBody SalaryDTO salaryDTO) {
        SalaryDTO newSalaryDTO = salaryService.addSalary(id, salaryDTO);
        return ResponseEntity.ok(newSalaryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaryDTO> updateSalary(@PathVariable("id") Integer id, @RequestBody SalaryDTO salaryDTO) {
        SalaryDTO updatedSalaryDTO = salaryService.updateSalary(id, salaryDTO);
        return ResponseEntity.ok(updatedSalaryDTO);
    }
}
