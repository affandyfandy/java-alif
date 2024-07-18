package com.example.employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @Column(name = "dept_no", length = 4)
    private String deptNo;

    @Column(name = "dept_name", length = 40)
    private String deptName;

    @OneToMany(mappedBy = "department")
    private Set<DeptManager> deptManagers;

    @OneToMany(mappedBy = "department")
    private Set<DeptEmp> deptEmps;
}
