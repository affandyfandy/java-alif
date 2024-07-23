package com.example.employee.service;

import com.example.employee.dto.SalaryDTO;

public interface SalaryService {
    SalaryDTO addSalary(Integer id, SalaryDTO salaryDTO);
    SalaryDTO updateSalary(Integer id, SalaryDTO salaryDTO);
}
