package org.example.assignment1;

import java.util.List;

public class Student {
    private String name;
    private int age;
    private Subject[] subjects;

    public Student(String name, int age, Subject[] subjects) {
        this.name = name;
        this.age = age;
        this.subjects = subjects;
    }

    public void learn() {
        System.out.print(name + " is learning ");
        if (subjects != null) {
            for (Subject subject : subjects) {
                System.out.print(subject.getName() + " for Class " + subject.getClassId() + ", ");
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Subject[] getSubjects() {
        return subjects;
    }

    public void setSubjects(Subject[] subjects) {
        this.subjects = subjects;
    }

    public static void main(String[] args) {
        Subject math = new Subject("Mathematics");
        math.setClassId("Math-101");
        Subject science = new Subject("Science");
        science.setClassId("Science-101");

        Subject[] subjects = {math, science};

        Student student = new Student("Alice", 15, subjects);
        student.learn();
    }
}
