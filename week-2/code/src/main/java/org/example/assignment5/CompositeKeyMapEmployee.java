package org.example.assignment5;

import java.util.*;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompositeKeyMapEmployee {
    public static void main(String[] args) {
        List<EmployeeComposite> employees = Arrays.asList(
            new EmployeeComposite(101, "John", "Sales"),
            new EmployeeComposite(103, "Michael", "IT"),
            new EmployeeComposite(102, "Charlie", "HR"),
            new EmployeeComposite(105, "David", "IT"),
            new EmployeeComposite(104, "Eve", "Sales")
        );

        // Convert List<EmployeeComposite> to Map<CompositeKey, EmployeeComposite>
        Map<MapKey, EmployeeComposite> employeeMap = employees.stream()
            .collect(Collectors.toMap(
                    emp -> new MapKey(emp.getDepartment(), emp.getEmployeeID()), // Composite key mapper
                    emp -> emp // Value mapper
            ));

        // Print the resulting map
        employeeMap.forEach((key, value) -> System.out.println(key + " -> " + value));
    }
}

class MapKey {
    private String department;
    private int employeeID;

    public MapKey(String department, int employeeID) {
        this.department = department;
        this.employeeID = employeeID;
    }

    public String getDepartment() {
        return department;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the compared objects are the same instance
        if (o == null || getClass() != o.getClass())
            return false; // Check if the compared object is null or not of the same class
        MapKey that = (MapKey) o; // Cast the object to MapKey
        // Check if both department and employeeID are equal
        return employeeID == that.employeeID && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, employeeID); // Generate a hash code using department and employeeID
    }

    @Override
    public String toString() {
        return "MapKey{" +
            "department='" + department + '\'' +
            ", employeeID=" + employeeID +
            '}';
    }
}

class EmployeeComposite {
    private int employeeID;
    private String name;
    private String department;

    public EmployeeComposite(int employeeID, String name, String department) {
        this.employeeID = employeeID;
        this.name = name;
        this.department = department;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}