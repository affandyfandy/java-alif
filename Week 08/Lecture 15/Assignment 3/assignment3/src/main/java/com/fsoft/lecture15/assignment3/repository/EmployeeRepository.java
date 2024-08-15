package com.fsoft.lecture15.assignment3.repository;

import com.fsoft.lecture15.assignment3.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
