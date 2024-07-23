package com.example.employee.entity;

import com.example.employee.dto.DeptEmpDTO;
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
@Builder
@IdClass(DeptEmpId.class)
public class DeptEmp {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_no", nullable = false)
    private Department department;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public DeptEmpDTO mapToDeptEmpDTO() {
        return DeptEmpDTO.builder()
                .empNo(this.getEmployee().getEmpNo())
                .deptNo(this.getDepartment().getDeptNo())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }
}
