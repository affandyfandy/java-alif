# Value Types and Reference Types

### How OOP principles perform in java?

Object-Oriented Programming (OOP) is a programming paradigm that uses classes and objects to structure software. There is four main principles of OOP, that is encapsulation, inheritance, polymorphism, and abstraction.

#### Encapsulation

Encapsulation is the mechanism of restricting direct access to some of an object’s attributes or methods and allowing modifications through specific method. Used to hide the internal state of the object and protect the object’s integrity.

```java
public class Person {
    private String name;
    private int age;

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
}
```

The Person class encapsulates the name and age fields by making them private and provides public getter and setter methods to access and modify these fields.

#### Inheritance

Inheritance allows a class to inherit the properties and methods of an existing class. It using the ‘extend’ keyword to inherit the class, also it promotes code reuse and establishes a hierarchy between classes.

```java
// Parent class
public class Animal {
    public void sound() {
        System.out.println("Animal sound");
    }
}

// Child class
class Dog extends Animal {

    @Override
    public void sound() {
        super.sound();
        System.out.println("Dog sound");
    }

    public void fetch() {
        System.out.println("Fetch");
    }
}
```

The Dog class inherits from the Animal class, so it can override or use the sound() method defined in Animal class, also the Dog class can make other method.

#### Polymorphism

Polymorphism allows objects of different classes to be treated as objects of a superclass.

```java
public static void main(String[] args) {
    Animal dog = new Dog();
    dog.sound(); // Output: Animal Sound Dog sound
}
```

The Dog class override the sound() method from Animal class. The dog variable declared as an Animal class, and creates a new instance of the Dog class.

#### Abstraction

Abstraction hiding the complex implementation details and showing only the necessary thing of an object.

```java
public abstract class Employee {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void work();
}

class Manager extends Employee {
    public Manager(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println("Manager " + super.getName() + " is managing");
    }
}
```

The Employee class is an abstract class that has work abstract method. The Manager class extends Employee class and must implement the work method.