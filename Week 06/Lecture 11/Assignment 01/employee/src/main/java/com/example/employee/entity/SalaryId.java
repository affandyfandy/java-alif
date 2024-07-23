package com.example.employee.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class SalaryId implements Serializable {
    private Integer employee;
    private LocalDate fromDate;

    public SalaryId() {
    }

    public SalaryId(Integer employee, LocalDate fromDate) {
        this.employee = employee;
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalaryId salaryId = (SalaryId) o;
        return Objects.equals(employee, salaryId.employee) &&
                Objects.equals(fromDate, salaryId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, fromDate);
    }
}
