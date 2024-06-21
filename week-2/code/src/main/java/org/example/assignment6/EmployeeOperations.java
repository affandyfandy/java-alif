package org.example.assignment6;

import java.util.*;

public class EmployeeOperations {
    public static void main(String[] args) {
        // List of employees
        List<Employee> employees = Arrays.asList(
                new Employee(101, "Alice", 30, 50000),
                new Employee(102, "David", 40, 70000),
                new Employee(103, "John", 32, 55000),
                new Employee(104, "Eve", 32, 55000),
                new Employee(105, "Charlie", 28, 45000),
                new Employee(106, "Bob", 35, 60000),
                new Employee(107, "John Doe", 33, 65000)
        );

        // Task 1: Sort name in alphabetical, ascending using streams
        System.out.println("\nSorted names alphabetically:");
        List<Employee> sortedEmployees = sortNameAlphabetically(employees);
        sortedEmployees.forEach(System.out::println);

        // Task 2: Find employee has max salary using streams
        List<Employee> maxSalaryEmployee = findMaxSalary(employees);

        System.out.println("\nEmployee with max salary:");
        maxSalaryEmployee.forEach(System.out::println);

        // Task 3: Check any employee names match with specific keywords or not
        String keyword = "John";
        List<Employee> matchingEmployees = findNameMatchingKeyword(employees, keyword);
        if (!matchingEmployees.isEmpty()) {
            System.out.println("\nThere is an employee with name matching '" + keyword + "'.");
            matchingEmployees.forEach(System.out::println);
        } else {
            System.out.println("\nNo employee found with name matching '" + keyword + "'.");
        }
    }

    public static List<Employee> sortNameAlphabetically(List<Employee> employees) {
        // Sort the list of employees by name alphabetically
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .toList();
    }

    public static List<Employee> findMaxSalary(List<Employee> employees) {
        // Find the employee with the maximum salary
        Optional<Employee> maxSalaryEmployee = employees.stream()
                .max(Comparator.comparingInt(Employee::getSalary));

        return maxSalaryEmployee.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    public static List<Employee> findNameMatchingKeyword(List<Employee> employees, String keyword) {
        // Find employees whose names contain the given keyword
        return employees.stream()
                .filter(employee -> employee.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}
