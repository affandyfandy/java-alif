package com.example.employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "dept_emp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(DeptEmpId.class)
public class DeptEmp {
    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Id
    @ManyToOne
    @JoinColumn(name = "dept_no", nullable = false)
    private Department department;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}