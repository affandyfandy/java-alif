package com.example.employee_manager.dao;

import com.example.employee_manager.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // SQL queries
    private final String INSERT_QUERY = "INSERT INTO Employee (ID, Name, Address, Department) VALUES (?, ?, ?, ?)";
    private final String SELECT_QUERY = "SELECT * FROM Employee";
    private final String SELECT_BY_ID_QUERY = "SELECT * FROM Employee WHERE ID = ?";
    private final String UPDATE_QUERY = "UPDATE Employee SET Name = ?, Address = ?, Department = ? WHERE ID = ?";
    private final String DELETE_QUERY = "DELETE FROM Employee WHERE ID = ?";

    // Create the Employee
    public int save(Employee employee) {
        return jdbcTemplate.update(INSERT_QUERY, employee.getId(), employee.getName(), employee.getAddress(), employee.getDepartment());
    }

    // Get all (Read) Employees
    public List<Employee> findAll() {
        return jdbcTemplate.query(SELECT_QUERY, new EmployeeRowMapper());
    }

    // Get Employee by ID
    public Employee findById(String id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new EmployeeRowMapper(), id);
    }

    // Update the Employee
    public int update(Employee employee) {
        return jdbcTemplate.update(UPDATE_QUERY, employee.getName(), employee.getAddress(), employee.getDepartment(), employee.getId());
    }

    // Delete the Employee
    public int delete(String id) {
        return jdbcTemplate.update(DELETE_QUERY, id);
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
