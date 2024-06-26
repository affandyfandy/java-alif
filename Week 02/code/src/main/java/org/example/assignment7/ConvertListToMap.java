package org.example.assignment7;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConvertListToMap {

    public static void main(String[] args) {
        // List of employees
        List<Employee> employees = List.of(
                new Employee(101, "Alice", 30, 50000),
                new Employee(102, "Bob", 35, 60000),
                new Employee(103, "Charlie", 28, 45000),
                new Employee(104, "David", 40, 70000),
                new Employee(105, "Eve", 32, 55000)
        );

        // Convert list of employees to a map with name as the key
        Map<Integer, Employee> employeeMapByName = listToMap(employees, Employee::getId);
        System.out.println("Employee Map by ID:");
        employeeMapByName.forEach((name, employee) -> System.out.println(name + " -> " + employee));
    }

    // Method to convert list of any object to map with any key field
    public static <T, K> Map<K, T> listToMap(List<T> list, Function<? super T, ? extends K> keyExtractor) {
        return list.stream()
            .collect(Collectors.toMap(
                keyExtractor, // Key extractor function
                Function.identity() // Value mapper
            ));
    }
}
