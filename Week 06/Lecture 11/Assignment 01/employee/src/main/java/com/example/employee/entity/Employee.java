package com.example.employee.entity;

import com.example.employee.common.Gender;
import com.example.employee.dto.EmployeeDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<Title> titles;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<DeptEmp> deptEmps;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<DeptManager> deptManagers;

    public EmployeeDTO mapToEmployeeDTO() {
        return EmployeeDTO.builder()
                .empNo(this.getEmpNo())
                .birthDate(this.getBirthDate())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .gender(this.getGender())
                .hireDate(this.getHireDate())
                .salaries(Optional.ofNullable(this.getSalaries())
                        .map(salaries -> salaries.stream()
                                .map(Salary::mapToSalaryDTO)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()))
                .titles(Optional.ofNullable(this.getTitles())
                        .map(titles -> titles.stream()
                                .map(Title::mapToTitleDTO)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()))
                .build();
    }
}