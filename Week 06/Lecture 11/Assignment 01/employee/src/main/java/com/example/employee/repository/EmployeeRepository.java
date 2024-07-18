package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * Find all employees with pagination
     * @param pageable the pagination information
     * @return
     */
    Page<Employee> findAll(Pageable pageable);
}
