package com.example.employee.dto;

import com.example.employee.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private String deptNo;
    private String deptName;
    private List<DeptEmpDTO> deptEmps;
    private List<DeptManagerDTO> deptManagers;

    public Department mapToDepartmentEntity() {
        return Department.builder()
                .deptNo(this.getDeptNo())
                .deptName(this.getDeptName())
                .build();
    }
}
