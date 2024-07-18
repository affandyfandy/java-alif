package com.example.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptManagerDTO {
    private String deptNo;
    private Integer empNo;
    private LocalDate fromDate;
    private LocalDate toDate;
}
