package com.example.employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

/**
 * The Salary class is an entity model object. It is used to map the salaries table in the database.
 * The class is annotated with JPA annotations to specify the table, primary key, and other constraints.
 * The class also contains the necessary constructors, getters, and setters.
 * The @IdClass annotation is used to specify the composite primary key class.
 */
@Entity
@Table(name = "salaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SalaryId.class)
public class Salary {

    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Column(name = "salary")
    private Integer salary;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
