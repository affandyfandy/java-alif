package org.example.assignment.src;

public class Person {
    private String name;
    private int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Public setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Public setter for age
    public void setAge(int age) {
        this.age = age;
    }

    // Public getter for name
    public String getName() {
        return name;
    }

    // Public getter for age
    public int getAge() {
        return age;
    }

    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }

    public static void main(String[] args) {
        Person patient = new Person("John", 20);
        Doctor doctor = new Doctor();
        doctor.treatPatient(patient); // Treating patient: Name: John, Age: 20
    }
}

class Doctor {
    public void treatPatient(Person patient) {
        System.out.println("Treating patient: ");
        patient.displayInfo();
    }
}