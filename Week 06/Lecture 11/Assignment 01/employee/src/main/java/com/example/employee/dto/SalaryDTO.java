package com.example.employee.dto;

import com.example.employee.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryDTO {
    private Integer salary;
    private LocalDate fromDate;
    private LocalDate toDate;

    public Salary mapToSalaryEntity() {
        return Salary.builder()
                .salary(this.getSalary())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }
}
