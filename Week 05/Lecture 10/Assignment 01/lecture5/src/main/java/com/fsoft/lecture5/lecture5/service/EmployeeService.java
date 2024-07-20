package com.fsoft.lecture5.lecture5.service;

import com.fsoft.lecture5.lecture5.dto.EmployeeDTO;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(UUID id);
    EmployeeDTO updateEmployee(UUID id, EmployeeDTO employeeDTO);
    void deleteEmployee(UUID id);
    List<EmployeeDTO> getEmployeesByDepartment(String department);
}

