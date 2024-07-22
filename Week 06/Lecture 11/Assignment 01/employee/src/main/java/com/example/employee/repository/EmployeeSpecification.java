package com.example.employee.repository;

import com.example.employee.common.Gender;
import com.example.employee.criteria.EmployeeSearchCriteria;
import com.example.employee.entity.Department;
import com.example.employee.entity.DeptEmp;
import com.example.employee.entity.Employee;
import com.example.employee.entity.Title;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification implements Specification<Employee> {
    private final EmployeeSearchCriteria criteria;

    public EmployeeSpecification(EmployeeSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        // Adding predicate for first name if specified
        if (criteria.getFirstName() != null) {
            predicates.add(builder.like(root.get("firstName"), "%" + criteria.getFirstName() + "%"));
        }

        // Adding predicate for last name if specified
        if (criteria.getLastName() != null) {
            predicates.add(builder.like(root.get("lastName"), "%" + criteria.getLastName() + "%"));
        }

        // Adding predicate for gender if specified
        if (criteria.getGender() != null) {
            predicates.add(builder.equal(root.get("gender"), Gender.valueOf(criteria.getGender())));
        }

        // Adding predicate for birth date range if specified
        if (criteria.getBirthDateFrom() != null || criteria.getBirthDateTo() != null) {
            if (criteria.getBirthDateFrom() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("birthDate"), criteria.getBirthDateFrom()));
            }
            if (criteria.getBirthDateTo() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("birthDate"), criteria.getBirthDateTo()));
            }
        }

        // Adding predicate for hire date range if specified
        if (criteria.getHireDateFrom() != null || criteria.getHireDateTo() != null) {
            if (criteria.getHireDateFrom() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("hireDate"), criteria.getHireDateFrom()));
            }
            if (criteria.getHireDateTo() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("hireDate"), criteria.getHireDateTo()));
            }
        }

        // Adding predicate for title if specified
        if (criteria.getTitle() != null) {
            Join<Employee, Title> titleJoin = root.join("titles", JoinType.LEFT);
            predicates.add(builder.like(titleJoin.get("title"), "%" + criteria.getTitle() + "%"));
        }

        // Adding predicate for salary range if specified
        if (criteria.getMinSalary() != null || criteria.getMaxSalary() != null) {
            Join<Employee, Title> salaryJoin = root.join("salaries", JoinType.LEFT);
            if (criteria.getMinSalary() != null) {
                predicates.add(builder.greaterThanOrEqualTo(salaryJoin.get("salary"), criteria.getMinSalary()));
            }
            if (criteria.getMaxSalary() != null) {
                predicates.add(builder.lessThanOrEqualTo(salaryJoin.get("salary"), criteria.getMaxSalary()));
            }
        }

        // Adding predicate for department name if specified
        if (criteria.getDepartmentName() != null) {
            Join<Employee, DeptEmp> deptEmpJoin = root.join("deptEmps", JoinType.LEFT);
            Join<DeptEmp, Department> departmentJoin = deptEmpJoin.join("department", JoinType.LEFT);
            predicates.add(builder.like(departmentJoin.get("deptName"), "%" + criteria.getDepartmentName() + "%"));
        }

        // Combine all predicates using AND
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
