package com.example.employee.service;

import com.example.employee.criteria.EmployeeSearchCriteria;
import com.example.employee.dto.EmployeeDTO;
import com.example.employee.dto.SalaryDTO;
import com.example.employee.dto.TitleDTO;
import com.example.employee.entity.Employee;
import com.example.employee.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Page<EmployeeDTO> getAllEmployees(Pageable pageable);
    EmployeeDTO getEmployeeById(Integer id);
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO);
    void deleteEmployee(Integer id);
    Page<EmployeeDTO> searchEmployees(EmployeeSearchCriteria criteria, Pageable pageable);
}
