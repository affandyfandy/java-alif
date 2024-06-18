package org.example.assignment1;

public class Teacher {
    private String name;
    private int age;
    private Subject subject;

    public Teacher(String name, int age, Subject subject) {
        this.name = name;
        this.age = age;
        this.subject = subject;
    }

    public Teacher(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Teacher(Subject subject) {
        this.subject = subject;
    }

    public Teacher() {}

    public void teach() {
        if (subject != null) {
            System.out.println("Teacher " + name + " is teaching " + subject.getName() + " for Class " + subject.getClassId());
        } else {
            System.out.println("Teacher " + name + " is teaching.");
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public static void main(String[] args) {
        Subject math = new Subject("Mathematics");
        math.setClassId("1");

        Teacher teacher = new Teacher("Tam", 30);
        teacher.setSubject(math);
        teacher.teach(); // Teacher Tam is teaching Mathematics for Class null
    }
}
