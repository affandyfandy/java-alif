package org.example.assignment5;

import java.util.*;
import java.util.stream.Collectors;

public class ConvertListToMap {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(101, "John"),
                new Employee(103, "Michael"),
                new Employee(102, "Charlie"),
                new Employee(105, "David"),
                new Employee(104, "Eve")
        );

        // Convert List<Employee> to Map<Integer, Employee> ordered by employeeID
        Map<Integer, Employee> employeeMap = employees.stream()
                .collect(Collectors.toMap(Employee::getEmployeeID, // Key mapper
                        emp -> emp)); // Value mapper

        // Print the resulting map
        employeeMap.forEach((key, value) -> System.out.println(key + " -> " + value));
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
}

