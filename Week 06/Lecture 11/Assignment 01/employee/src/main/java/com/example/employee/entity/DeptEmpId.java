package com.example.employee.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

public class DeptEmpId implements Serializable {
    private Integer employee;
    private String department;

    public DeptEmpId() {
    }

    public DeptEmpId(Integer employee, String department) {
        this.employee = employee;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptEmpId deptEmpId = (DeptEmpId) o;
        return Objects.equals(employee, deptEmpId.employee) && Objects.equals(department, deptEmpId.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, department);
    }
}
