package org.example.assignment8;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class SerializationDeserializationFile {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("John Doe", 30, "IT"),
            new Employee("Jane Doe", 25, "HR"),
            new Employee("Tom Smith", 35, "Finance"),
            new Employee("Jerry Seinfeld", 40, "Marketing")
        );

        // Serialization of employees
        String fileName = "employees.ser";

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) { // ObjectOutputStream is used to serialize an object
            out.writeObject(employees); // writeObject() method serializes the object and writes it to the file
            System.out.println("Serialized data is saved in employees.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        // Deserialization of employees.ser
        List<Employee> employeesDes = null;

        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) { // ObjectInputStream is used to deserialize an object
            employeesDes = (List<Employee>) in.readObject(); // readObject() method deserializes the object from the file
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }

        if (employeesDes != null) {
            System.out.println("\n Deserialized data from employees.ser");
            for (Employee emp : employeesDes) {
                System.out.println(emp);
            }
        } else {
            System.out.println("No employees found");
        }
    }
}

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private String department;

    public Employee(String name, int age, String department) {
        this.name = name;
        this.age = age;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{name='" + name + "', age=" + age + ", department='" + department + "'}";
    }
}
