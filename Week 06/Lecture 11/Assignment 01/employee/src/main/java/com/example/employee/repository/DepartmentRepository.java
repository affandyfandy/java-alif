package com.example.employee.repository;

import com.example.employee.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.deptEmps LEFT JOIN FETCH d.deptManagers")
    List<Department> findAllWithFetch();

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.deptEmps LEFT JOIN FETCH d.deptManagers WHERE d.deptNo = :deptNo")
    Optional<Department> findByDeptNoWithFetch(@Param("deptNo") String deptNo);
}
