package com.example.employee.entity;

import java.io.Serializable;
import java.util.Objects;

public class DeptManagerId implements Serializable {
    private Integer employee;
    private String department;

    public DeptManagerId() {
    }

    public DeptManagerId(Integer employee, String department) {
        this.employee = employee;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptManagerId that = (DeptManagerId) o;
        return Objects.equals(employee, that.employee) && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, department);
    }
}
