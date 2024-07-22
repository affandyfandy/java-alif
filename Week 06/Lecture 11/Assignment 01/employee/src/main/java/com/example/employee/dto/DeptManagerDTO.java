package com.example.employee.dto;

import com.example.employee.entity.Department;
import com.example.employee.entity.DeptManager;
import com.example.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptManagerDTO {
    private String deptNo;
    private Integer empNo;
    private LocalDate fromDate;
    private LocalDate toDate;

    public DeptManager mapToDeptManagerEntity(Department department, Employee employee) {
        return DeptManager.builder()
                .department(department)
                .employee(employee)
                .fromDate(this.fromDate)
                .toDate(this.toDate)
                .build();
    }
}
