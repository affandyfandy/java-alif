package com.example.employee.service.impl;

import com.example.employee.dto.SalaryDTO;
import com.example.employee.entity.Employee;
import com.example.employee.entity.Salary;
import com.example.employee.entity.SalaryId;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.SalaryRepository;
import com.example.employee.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final SalaryRepository salaryRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public SalaryServiceImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Add salary for employee
     * @param id employee id
     * @param salaryDTO salary information
     * @return salary DTO
     */
    @Override
    public SalaryDTO addSalary(Integer id, SalaryDTO salaryDTO) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Employee not found"));
        Salary salary = new Salary();
        salary.setEmployee(employee);
        salary.setSalary(salaryDTO.getSalary());
        salary.setFromDate(salaryDTO.getFromDate());
        salary.setToDate(salaryDTO.getToDate());
        salary = salaryRepository.save(salary);
        return mapToSalaryDTO(salary);
    }

    /**
     * Update salary for employee
     * @param id employee id
     * @param salaryDTO salary information
     * @return updated salary DTO
     */
    @Override
    public SalaryDTO updateSalary(Integer id, SalaryDTO salaryDTO) {
        Salary salary = salaryRepository.findById(new SalaryId(id, salaryDTO.getFromDate())).
                orElseThrow(() -> new RuntimeException("Salary not found"));
        salary.setSalary(salaryDTO.getSalary());
        salary.setToDate(salaryDTO.getToDate());
        salary = salaryRepository.save(salary);
        return mapToSalaryDTO(salary);
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
}
