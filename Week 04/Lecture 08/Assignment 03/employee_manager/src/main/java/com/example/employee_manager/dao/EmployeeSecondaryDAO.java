package com.example.employee_manager.dao;

import com.example.employee_manager.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeSecondaryDAO {

    private final JdbcTemplate jdbcTemplate;

    // Inject the secondaryJdbcTemplate using constructor injection
    @Autowired
    public EmployeeSecondaryDAO(@Qualifier("secondaryJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL queries
    private final String INSERT_QUERY = "INSERT INTO Employee (ID, Name, Address, Department) VALUES (?, ?, ?, ?)";
    private final String SELECT_QUERY = "SELECT * FROM Employee";
    private final String SELECT_BY_ID_QUERY = "SELECT * FROM Employee WHERE ID = ?";
    private final String UPDATE_QUERY = "UPDATE Employee SET Name = ?, Address = ?, Department = ? WHERE ID = ?";
    private final String DELETE_QUERY = "DELETE FROM Employee WHERE ID = ?";

    // Create the Employee
    @Transactional
    public int save(Employee employee) {
        try {
            return jdbcTemplate.update(INSERT_QUERY, employee.getId(), employee.getName(), employee.getAddress(), employee.getDepartment());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the employee. Error: " + e.getMessage());
        }
    }

    // Get all (Read) Employees
    public List<Employee> findAll() {
        try {
            return jdbcTemplate.query(SELECT_QUERY, new EmployeeRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all employees. Error: " + e.getMessage());
        }
    }

    // Get Employee by ID
    public Employee findById(String id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new EmployeeRowMapper(), id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get employee by ID. Error: " + e.getMessage());
        }
    }

    // Update the Employee
    @Transactional
    public int update(Employee employee) {
        try {
            return jdbcTemplate.update(UPDATE_QUERY, employee.getName(), employee.getAddress(), employee.getDepartment(), employee.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update the employee. Error: " + e.getMessage());
        }
    }

    // Delete the Employee
    @Transactional
    public int delete(String id) {
        try {
            return jdbcTemplate.update(DELETE_QUERY, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete the employee. Error: " + e.getMessage());
        }
    }

    // RowMapper to map the ResultSet to Employee
    private static final class EmployeeRowMapper implements RowMapper<Employee> {
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getString("ID"));
            employee.setName(rs.getString("Name"));
            employee.setAddress(rs.getString("Address"));
            employee.setDepartment(rs.getString("Department"));
            return employee;
        }
    }
}
