package com.example.employee.service.impl;

import com.example.employee.dto.DepartmentDTO;
import com.example.employee.dto.DeptEmpDTO;
import com.example.employee.dto.DeptManagerDTO;
import com.example.employee.entity.Department;
import com.example.employee.entity.DeptEmp;
import com.example.employee.entity.DeptManager;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(Department::mapToDepartmentDTO).toList();
    }

    @Override
    public DepartmentDTO getDepartmentById(String id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return department.mapToDepartmentDTO();
    }

    @Override
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentDTO.mapToDepartmentEntity();
        Department savedDepartment = departmentRepository.save(department);
        return savedDepartment.mapToDepartmentDTO();
    }

    @Override
    public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setDeptName(departmentDTO.getDeptName());
        Department updatedDepartment = departmentRepository.save(department);
        return updatedDepartment.mapToDepartmentDTO();
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }
}
