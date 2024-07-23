package com.example.employee.entity;

import com.example.employee.dto.SalaryDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "salaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SalaryId.class)
public class Salary {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Column(name = "salary")
    private Integer salary;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public SalaryDTO mapToSalaryDTO() {
        return SalaryDTO.builder()
                .salary(this.getSalary())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }
}
