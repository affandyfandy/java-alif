package com.example.employee.entity;

import com.example.employee.common.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "first_name", length = 14)
    private String firstName;

    @Column(name = "last_name", length = 16)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<Salary> salaries;

    @OneToMany(mappedBy = "employee")
    private Set<Title> titles;

    @OneToMany(mappedBy = "employee")
    private Set<DeptEmp> deptEmps;

    @OneToMany(mappedBy = "employee")
    private Set<DeptManager> deptManagers;
}