package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptEmpDTO {
    private Integer empNo;
    private String deptNo;
    private LocalDate fromDate;
    private LocalDate toDate;
}