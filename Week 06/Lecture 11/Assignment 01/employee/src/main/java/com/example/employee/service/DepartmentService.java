package com.example.employee.service;

import com.example.employee.dto.DepartmentDTO;
import com.example.employee.entity.Department;

import java.util.List;

public interface DepartmentService {
    public List<DepartmentDTO> getAllDepartments();
    public DepartmentDTO getDepartmentById(String id);
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO);
    public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO);
    public void deleteDepartment(String id);
}
