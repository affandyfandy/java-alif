package com.example.employee.dto;

import com.example.employee.entity.Department;
import com.example.employee.entity.DeptEmp;
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
public class DeptEmpDTO {
    private Integer empNo;
    private String deptNo;
    private LocalDate fromDate;
    private LocalDate toDate;

    public DeptEmp mapToDeptEmpEntity(Employee employee, Department department) {
        return DeptEmp.builder()
                .employee(employee)
                .department(department)
                .fromDate(this.fromDate)
                .toDate(this.toDate)
                .build();
    }
}
