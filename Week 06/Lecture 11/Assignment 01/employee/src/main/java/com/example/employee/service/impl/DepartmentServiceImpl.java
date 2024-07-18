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
        return departments.stream().map(this::mapToDepartmentDTO).toList();
    }

    @Override
    public DepartmentDTO getDepartmentById(String id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToDepartmentDTO(department);
    }

    @Override
    public DepartmentDTO addDepartment(DepartmentDTO departmentDTO) {
        Department department = mapToDepartment(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return mapToDepartmentDTO(savedDepartment);
    }

    @Override
    public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setDeptName(departmentDTO.getDeptName());
        Department updatedDepartment = departmentRepository.save(department);
        return mapToDepartmentDTO(updatedDepartment);
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }

    private DepartmentDTO mapToDepartmentDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDeptNo(department.getDeptNo());
        departmentDTO.setDeptName(department.getDeptName());
        departmentDTO.setDeptEmps(Optional.ofNullable(department.getDeptEmps())
                .map(deptEmps -> deptEmps.stream()
                        .map(this::mapToDeptEmpDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
        departmentDTO.setDeptManagers(Optional.ofNullable(department.getDeptManagers())
                .map(deptManagers -> deptManagers.stream()
                        .map(this::mapToDeptManagerDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
        return departmentDTO;
    }

    private Department mapToDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setDeptNo(departmentDTO.getDeptNo());
        department.setDeptName(departmentDTO.getDeptName());
        return department;
    }

    private DeptEmpDTO mapToDeptEmpDTO(DeptEmp deptEmp) {
        DeptEmpDTO deptEmpDTO = new DeptEmpDTO();
        deptEmpDTO.setEmpNo(deptEmp.getEmployee().getEmpNo());
        deptEmpDTO.setDeptNo(deptEmp.getDepartment().getDeptNo());
        deptEmpDTO.setFromDate(deptEmp.getFromDate());
        deptEmpDTO.setToDate(deptEmp.getToDate());
        return deptEmpDTO;
    }

    private DeptManagerDTO mapToDeptManagerDTO(DeptManager deptManager) {
        DeptManagerDTO deptManagerDTO = new DeptManagerDTO();
        deptManagerDTO.setEmpNo(deptManager.getEmployee().getEmpNo());
        deptManagerDTO.setDeptNo(deptManager.getDepartment().getDeptNo());
        deptManagerDTO.setFromDate(deptManager.getFromDate());
        deptManagerDTO.setToDate(deptManager.getToDate());
        return deptManagerDTO;
    }
}
