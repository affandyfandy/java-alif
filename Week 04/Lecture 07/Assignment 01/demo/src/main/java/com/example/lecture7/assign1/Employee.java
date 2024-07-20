package com.example.lecture7.assign1;

import org.springframework.beans.factory.annotation.Autowired;

public class Employee {
    private int id;
    private String name;
    private int age;
    private EmployeeWork employeeWork;

    // Field based dependency injection
//    @Autowired
//    private EmployeeWork employeeWork;

    public Employee(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Setter based dependency injection
    @Autowired
    public void setEmployeeWork(EmployeeWork employeeWork) {
        this.employeeWork = employeeWork;
    }

    public void working() {
        System.out.println("My Name is: " + name);
        employeeWork.work();
    }
}

