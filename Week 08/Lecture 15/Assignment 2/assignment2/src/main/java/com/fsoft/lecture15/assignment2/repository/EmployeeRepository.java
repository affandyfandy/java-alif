package com.fsoft.lecture15.assignment2.repository;

import com.fsoft.lecture15.assignment2.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
