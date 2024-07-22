package com.example.employee.entity;

import com.example.employee.dto.DeptManagerDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "dept_manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(DeptManagerId.class)
public class DeptManager {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_no", nullable = false)
    private Department department;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public DeptManagerDTO mapToDeptManagerDTO() {
        return DeptManagerDTO.builder()
                .deptNo(this.getDepartment().getDeptNo())
                .empNo(this.getEmployee().getEmpNo())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }
}
