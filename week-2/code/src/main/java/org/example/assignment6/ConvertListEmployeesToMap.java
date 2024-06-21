package org.example.assignment6;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConvertListEmployeesToMap {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(101, "Alice", 30, 50000),
                new Employee(102, "Bob", 35, 60000),
                new Employee(103, "Charlie", 28, 45000),
                new Employee(104, "David", 40, 70000),
                new Employee(105, "Eve", 32, 55000)
        );

        Map<Integer, Employee> employeeMap = convertToMap(employees);
    }

    public static Map<Integer, Employee> convertToMap(List<Employee> employees) {
        // Convert the list of employees to a map with employee ID as the key
        // and the employee object as the value
        Map<Integer, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getId, employee -> employee));

        // Print the resulting map
        employeeMap.forEach((id, employee) -> System.out.println(id + ": " + employee));

        return employeeMap;
    }
}
