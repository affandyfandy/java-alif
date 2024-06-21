package org.example.assignment7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RemoveDuplicateAny {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee(101, "Alice", 30, 50000),
                new Employee(102, "Bob", 35, 60000),
                new Employee(103, "Charlie", 28, 45000),
                new Employee(104, "David", 40, 70000),
                new Employee(105, "Eve", 32, 55000),
                new Employee(102, "Bob Duplicate", 36, 65000),
                new Employee(106, "Alice", 31, 55000)
        );

        // Remove duplicates based on employee ID
        List<Employee> uniqueEmployeesById = removeDuplicates(employees, Employee::getId);
        System.out.println("Unique employees by ID:");
        uniqueEmployeesById.forEach(System.out::println);

        // Remove duplicates based on employee name
        List<Employee> uniqueEmployeesByName = removeDuplicates(employees, Employee::getName);
        System.out.println("\nUnique employees by name:");
        uniqueEmployeesByName.forEach(System.out::println);
    }

    public static <T, R> List<T> removeDuplicates(List<T> list, Function<? super T, ? extends R> keyExtractor) {
        return new ArrayList<>(list.stream()
                .collect(Collectors.toMap(
                        keyExtractor, // Specifies the key
                        Function.identity(), // Value itself
                        (existing, replacement) -> existing // Merge function to handle duplicates
                ))
                .values());
    }
}