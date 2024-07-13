package com.fsoft.lecture5.lecture5.service.impl;

import com.fsoft.lecture5.lecture5.dto.EmployeeDTO;
import com.fsoft.lecture5.lecture5.exception.NoSuchEmployeeExistsException;
import com.fsoft.lecture5.lecture5.mapper.EmployeeMapper;
import com.fsoft.lecture5.lecture5.model.Employee;
import com.fsoft.lecture5.lecture5.repository.EmployeeRepository;
import com.fsoft.lecture5.lecture5.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEmployee(employeeDTO);
        employee.setId(UUID.randomUUID());
        return employeeMapper.toEmployeeDTO(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(employeeMapper::toEmployeeDTO).toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchEmployeeExistsException("Employee not found"));
        return employeeMapper.toEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(UUID id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchEmployeeExistsException("Employee not found"));

        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setDateOfBirth(employeeDTO.getDateOfBirth());
        existingEmployee.setAddress(employeeDTO.getAddress());
        existingEmployee.setDepartment(employeeDTO.getDepartment());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setPhone(employeeDTO.getPhone());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.toEmployeeDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NoSuchEmployeeExistsException("Employee with ID " + id + " not found."));
        employeeRepository.delete(employee);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        List<Employee> employees = employeeRepository.findByDepartment(department);
        return employees.stream()
                .map(employeeMapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }
}
