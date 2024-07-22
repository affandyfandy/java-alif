package com.example.employee.service.impl;

import com.example.employee.criteria.EmployeeSearchCriteria;
import com.example.employee.dto.EmployeeDTO;
import com.example.employee.dto.SalaryDTO;
import com.example.employee.dto.TitleDTO;
import com.example.employee.entity.*;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.repository.EmployeeSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Get all employees with pagination
     * @param pageable pagination information
     * @return page of employee DTO
     */
    @Override
    @Transactional
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.map(Employee::mapToEmployeeDTO);
    }

    /**
     * Get employee by id
     * @param id employee id
     * @return employee DTO
     */
    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findByIdWithFetch(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        return employee.mapToEmployeeDTO();
    }

    /**
     * Add employee
     * @param employeeDTO employee information
     * @return employee DTO
     */
    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.mapToEmployeeEntity();
        employee = employeeRepository.save(employee);
        return employee.mapToEmployeeDTO();
    }

    /**
     * Update employee
     * @param id employee id
     * @param employeeDTO employee information
     * @return updated employee DTO
     */
    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findByIdWithFetch(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setHireDate(employeeDTO.getHireDate());
        employeeRepository.save(employee);
        return employee.mapToEmployeeDTO();
    }

    /**
     * Delete employee
     * @param id employee id
     */
    @Override
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    /**
     * Search employees
     * @param criteria search criteria
     * @param pageable pagination information
     * @return page of employee DTO
     */
    @Override
    public Page<EmployeeDTO> searchEmployees(EmployeeSearchCriteria criteria, Pageable pageable) {
        EmployeeSpecification specification = new EmployeeSpecification(criteria);
        Page<Employee> employees = employeeRepository.findAll(specification, pageable);
        return employees.map(Employee::mapToEmployeeDTO);
    }
}
