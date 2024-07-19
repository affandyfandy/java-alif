package com.example.employee.criteria;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeSearchCriteria {
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private LocalDate hireDateFrom;
    private LocalDate hireDateTo;
    private String title;
    private Integer minSalary;
    private Integer maxSalary;
    private String departmentName;
}
