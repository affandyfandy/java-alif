package com.example.employee.entity;

import com.example.employee.dto.DepartmentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @Column(name = "dept_no", length = 4)
    private String deptNo;

    @Column(name = "dept_name", length = 40)
    private String deptName;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<DeptManager> deptManagers;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<DeptEmp> deptEmps;

    public DepartmentDTO mapToDepartmentDTO() {
        return DepartmentDTO.builder()
                .deptNo(this.getDeptNo())
                .deptName(this.getDeptName())
                .deptEmps(this.getDeptEmps().stream().map(DeptEmp::mapToDeptEmpDTO).collect(Collectors.toList()))
                .deptManagers(this.getDeptManagers().stream().map(DeptManager::mapToDeptManagerDTO).collect(Collectors.toList()))
                .build();
    }
}
