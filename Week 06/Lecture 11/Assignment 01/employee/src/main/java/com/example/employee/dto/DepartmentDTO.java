package com.example.employee.dto;

import com.example.employee.entity.DeptEmp;
import com.example.employee.entity.DeptManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private String deptNo;
    private String deptName;
    private List<DeptEmpDTO> deptEmps;
    private List<DeptManagerDTO> deptManagers;
}
