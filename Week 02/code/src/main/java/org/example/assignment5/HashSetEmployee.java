package org.example.assignment5;

import java.util.*;
import java.util.Objects;
import java.util.stream.Collectors;

public class HashSetEmployee {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(101, "John"),
                new Employee(103, "Michael"),
                new Employee(102, "Charlie"),
                new Employee(105, "David"),
                new Employee(104, "Eve"),
                new Employee(103, "Michael Duplicate") // Duplicate employeeID
        );

        // Convert List<Employee> to Map<Integer, Employee> ordered by employeeID
        Map<Integer, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getEmployeeID, // Key mapper
                        emp -> emp, // Value mapper
                        (existing, replacement) -> existing)); // Handle duplicate keys by keeping the existing value

        // Print the resulting map
        employeeMap.forEach((key, value) -> System.out.println(key + " -> " + value));

        // Add employees to a HashSet to recognize duplicates by employee ID
        Set<Employee> employeeSet = new HashSet<>(employees);

        // Print the HashSet to show it contains no duplicates by employee ID
        System.out.println("\nHashSet of Employees:");
        employeeSet.forEach(System.out::println);
    }
}

class Employee {
    private int employeeID;
    private String name;

    // Constructor, getters, setters
    public Employee(int employeeID, String name) {
        this.employeeID = employeeID;
        this.name = name;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the compared objects are the same instance
        if (o == null || getClass() != o.getClass()) return false; // Check if the compared object is null or not of the same class
        Employee employee = (Employee) o; // Cast the object to Employee
        return employeeID == employee.employeeID; // Check if the employee IDs are equal
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID); // Generate a hash code using employeeID
    }
}
