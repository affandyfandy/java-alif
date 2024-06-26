package org.example.assignment5;

import java.util.*;
import java.util.stream.Collectors;

public class ConvertListToMap {
    public static void main(String[] args) {
        List<EmployeeMap> employees = Arrays.asList(
                new EmployeeMap(101, "John"),
                new EmployeeMap(103, "Michael"),
                new EmployeeMap(102, "Charlie"),
                new EmployeeMap(105, "David"),
                new EmployeeMap(104, "Eve")
        );

        // Convert List<EmployeeMap> to Map<Integer, EmployeeMap> ordered by employeeID
        Map<Integer, EmployeeMap> employeeMap = employees.stream()
                .collect(Collectors.toMap(EmployeeMap::getEmployeeID, // Key mapper
                        emp -> emp)); // Value mapper

        // Print the resulting map
        employeeMap.forEach((key, value) -> System.out.println(key + " -> " + value));
    }
}

class EmployeeMap {
    private int employeeID;
    private String name;

    // Constructor, getters, setters
    public EmployeeMap(int employeeID, String name) {
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

