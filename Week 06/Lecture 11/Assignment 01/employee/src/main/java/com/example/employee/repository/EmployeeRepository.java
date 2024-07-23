package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    /**
     * Find all employees with pagination
     * @param pageable the pagination information
     * @return
     */
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.salaries LEFT JOIN FETCH e.titles LEFT JOIN FETCH e.deptEmps LEFT JOIN FETCH e.deptManagers")
    Page<Employee> findAll(Pageable pageable);

    /**
     * Find employee by id
     * @param empNo the employee id
     * @return
     */
    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.salaries LEFT JOIN FETCH e.titles LEFT JOIN FETCH e.deptEmps LEFT JOIN FETCH e.deptManagers WHERE e.empNo = :empNo")
    Optional<Employee> findByIdWithFetch(@Param("empNo") Integer empNo);
}
