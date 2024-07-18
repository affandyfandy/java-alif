package com.example.employee.service.impl;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.dto.SalaryDTO;
import com.example.employee.dto.TitleDTO;
import com.example.employee.entity.*;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.SalaryRepository;
import com.example.employee.repository.TitleRepository;
import com.example.employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        return employees.map(this::mapToEmployeeDTO);
    }

    /**
     * Get employee by id
     * @param id employee id
     * @return employee DTO
     */
    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        return mapToEmployeeDTO(employee);
    }

    /**
     * Add employee
     * @param employeeDTO employee information
     * @return employee DTO
     */
    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapToEmployeeEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return mapToEmployeeDTO(employee);
    }

    /**
     * Update employee
     * @param id employee id
     * @param employeeDTO employee information
     * @return updated employee DTO
     */
    @Override
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setHireDate(employeeDTO.getHireDate());
        employeeRepository.save(employee);
        return mapToEmployeeDTO(employee);
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
     * Map employee entity to employee DTO
     * @param employee employee entity
     * @return employee DTO
     */
    private EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpNo(employee.getEmpNo());
        employeeDTO.setBirthDate(employee.getBirthDate());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setGender(employee.getGender());
        employeeDTO.setHireDate(employee.getHireDate());
        employeeDTO.setSalaries(Optional.ofNullable(employee.getSalaries())
                .map(salaries -> salaries.stream()
                        .map(this::mapToSalaryDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
        employeeDTO.setTitles(Optional.ofNullable(employee.getTitles())
                .map(titles -> titles.stream()
                        .map(this::mapToTitleDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
        return employeeDTO;
    }

    /**
     * Map employee DTO to employee entity
     * @param salary salary entity
     * @return salary DTO
     */
    private SalaryDTO mapToSalaryDTO(Salary salary) {
        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setSalary(salary.getSalary());
        salaryDTO.setFromDate(salary.getFromDate());
        salaryDTO.setToDate(salary.getToDate());
        return salaryDTO;
    }

    /**
     * Map title entity to title DTO
     * @param title title entity
     * @return title DTO
     */
    private TitleDTO mapToTitleDTO(Title title) {
        TitleDTO titleDTO = new TitleDTO();
        titleDTO.setTitle(title.getTitle());
        titleDTO.setFromDate(title.getFromDate());
        titleDTO.setToDate(title.getToDate());
        return titleDTO;
    }

    /**
     * Map employee DTO to employee entity
     * @param employeeDTO employee DTO
     * @return employee entity
     */
    private Employee mapToEmployeeEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmpNo(employeeDTO.getEmpNo());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setGender(employeeDTO.getGender());
        employee.setHireDate(employeeDTO.getHireDate());
        return employee;
    }
}
