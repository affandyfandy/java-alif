# Value Types and Reference Types

## How OOP principles perform in java?

Object-Oriented Programming (OOP) is a programming paradigm that uses classes and objects to structure software. There is four main principles of OOP, that is encapsulation, inheritance, polymorphism, and abstraction.

#
### Encapsulation

Encapsulation is the mechanism of restricting direct access to some of an object’s attributes or methods and allowing modifications through specific method. Used to hide the internal state of the object and protect the object’s integrity.

```java
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
```

The Person class encapsulates the name and age fields by making them private and provides public getter and setter methods to access and modify these fields. The Doctor class has a treatPatient method, which takes a Person object as a parameter, and will take the method of Person class through patient as parameter.

#
### Inheritance

Inheritance allows a class to inherit the properties and methods of an existing class. It using the ‘extend’ keyword to inherit the class, also it promotes code reuse and establishes a hierarchy between classes.

```java
// Parent class
public class Animal {
    public void sound() {
        System.out.println("Animal sound");
    }

    public void eat() {
        System.out.println("Animal is eating.");
    }

    public static void main(String[] args) {
        Dog myDog = new Dog();
        DogOwner dogOwner = new DogOwner();
        dogOwner.feedAnimal(myDog); // Animal is eating.
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

class DogOwner {
    public void feedAnimal(Dog dog){
        dog.eat();
    }
}
```

The Dog class inherits from the Animal class, so it can override or use the sound() method defined in Animal class, also the Dog class can make other method. The ZooKeeper class has a feedAnimal method, which takes an Animal object as a parameter and use the parent's method.

#
### Polymorphism

Polymorphism allows objects of different classes to be treated as objects of a superclass.

```java
public static void main(String[] args) {
    Dog myDog = new Dog();

    AnimalShelter animalShelter = new AnimalShelter();
    animalShelter.adopt(myDog); // You adopted an animal that says: Animal sound Dog sound
}

class AnimalShelter {
    public void adopt(Animal animal) {
        System.out.print("You adopted an animal that says: ");
        animal.sound();
    }
}
```

The Dog class override the sound() method from Animal class. The dog variable declared as an Animal class, and creates a new instance of the Dog class. The AnimalShelter class has an adopt method that takes an Animal object as a parameter and calls its sound method.

#
### Abstraction

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

    
    public static void main(String[] args) {
        Company company = new Company();
        Employee manager = new Manager("John");

        company.assignTask(manager);    // Outputs: Task assigned to: Manager John is managing
    }
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

class Company {
    public void assignTask(Employee employee) {
        System.out.println("Task assigned to:");
        employee.work();
    }
}
```

The Employee class is an abstract class that has work abstract method. The Manager class extends Employee class and must implement the work method. The Company class has an assignTask method that takes an Employee object as a parameter and calls its work abstract method.

#
## Passing an object into function, and then assign that object to new one

If assign the passed object reference to a new object in the parameters function, the reference inside the function will point to the new object, but this change will not affect the original reference outside the function.

```java
public class MyClass {
    public static void main(String[] args) {
        MyObject myObj = new MyObject(10);
        System.out.println("Before reassignment: " + myObj.value); // Outputs: 10

        reassignObject(myObj);
        System.out.println("After reassignment: " + myObj.value); // Still, outputs: 10
    }
    
    public static void reassignObject(MyObject obj) {
        obj = new MyObject(30); // Reassigning to a new object
    }
}

class MyObject {
    int value;

    MyObject(int value) {
        this.value = value;
    }
}
```

The reassignObject function assigns obj to a new MyObject with a value of 30. However, this reassignment only affects the local copy of the reference inside the function. The original reference myObj in the main method still points to the original object with a value of 10.