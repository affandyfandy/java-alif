package com.example.employee.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class TitleId {
    private Integer employee;
    private String title;
    private LocalDate fromDate;

    public TitleId() {
    }

    public TitleId(Integer employee, String title, LocalDate fromDate) {
        this.employee = employee;
        this.title = title;
        this.fromDate = fromDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TitleId titleId = (TitleId) o;
        return Objects.equals(employee, titleId.employee) &&
                Objects.equals(title, titleId.title) &&
                Objects.equals(fromDate, titleId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, title, fromDate);
    }
}
