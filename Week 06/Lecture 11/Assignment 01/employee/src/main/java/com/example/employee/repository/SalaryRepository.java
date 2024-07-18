package com.example.employee.repository;

import com.example.employee.entity.Salary;
import com.example.employee.entity.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
}
