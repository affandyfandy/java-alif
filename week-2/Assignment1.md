# Assignment 1
[Code and Java File - Assignment 1](code/src/main/java/org/example/assignment1/)

## LAB 1
A dog has:
- States - color, name, breed
- Behaviors - wagging the tail, barking, eating

```java
public class Dog {
    private String color;
    private String name;
    private String breed;

    public Dog(String color, String name, String breed) {
        this.color = color;
        this.name = name;
        this.breed = breed;
    }

    public void wagTail() {
        System.out.println(name + " is wagging its tail.");
    }

    public void bark() {
        System.out.println(name + " is barking.");
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    ... //getter and setter
}
```

#
## LAB 2

**Teacher Class**
- Teacher with state is name, age, subject and the behavior is teaching
- We can create teacher  with name and age
- We can create teacher with subject (class)
- Print to console info: "Teacher Tam teaching Mathematics for Class 1"

```java
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

    public static void main(String[] args) {
        Subject math = new Subject("Mathematics");
        math.setClassId("1");

        Teacher teacher = new Teacher("Tam", 30);
        teacher.setSubject(math);
        teacher.teach(); // Teacher Tam is teaching Mathematics for Class 1
    }

    ... // getter and setter
}
```

**Subject Class**
- Subject with state is name and classId
- We can create Subject with name

```java
public class Subject {
    private String classId;
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
    ... // getter and setter
}
```
#
## LAB 3

**Student class**
- Student with name, age, and behavior is learning
- The student will learning some subject (array)

```java
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
    
    public static void main(String[] args) {
        Subject math = new Subject("Mathematics");
        math.setClassId("Math-101");
        Subject science = new Subject("Science");
        science.setClassId("Science-101");

        Subject[] subjects = {math, science};

        Student student = new Student("Alice", 15, subjects);
        student.learn();
    }

    ... // getter and setter
}
```

**Teacher class**
- The teacher will teaching a subject

```java
public static void main(String[] args) {
    Subject math = new Subject("Mathematics");
    math.setClassId("1");

    Teacher teacher = new Teacher("Tam", 30);
    teacher.setSubject(math);
    teacher.teach(); // Teacher Tam is teaching Mathematics for Class 1
}
```
