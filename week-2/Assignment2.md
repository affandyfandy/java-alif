# Assignment 2
[Code and Java File - Assignment 2](code/src/main/java/org/example/assignment2/)

## Q: Slide 10: what happen if implement 2 interface have same default method? How to solve? Demo in code.

When a class implements two interfaces that have the same default method, it results in a conflict, and the implementing class must override the conflicting method to resolve the ambiguity of the same default method.

Resolve the conflict in MyClass by explicitly override the same default method that is log and provide its own implementation.

```java
public class MyClass implements FirstInterface, SecondInterface {
    @Override
    public void firstMethod() {
        System.out.println("This is first method");
    }

    @Override
    public void secondMethod() {
        System.out.println("This is second method");
    }

    @Override
    public void log(String string) {
        System.out.println("Custom log method");
        FirstInterface.super.log(string);
        SecondInterface.super.log(string);
    }

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.firstMethod();
        myClass.secondMethod();
        myClass.log("Hello");
        // Output:
        // This is first method
        // This is second method
        // Custom log method
        // This method is default implementation of FirstInterface Hello
        // This method is default implementation of SecondInterface Hello
    }
}
```

Output:
This is first method
This is second method
Custom log method
This method is default implementation of FirstInterface Hello
This method is default implementation of SecondInterface Hello

#
## Q: Explain the difference between abstract class and interface (Syntax and Purpose)

In Java, abstract classes and interface used to achieve abstraction, but have different syntax and purposes.

**Abstract class**

Syntax:
- Defined using 'abstract' keyword
- It can have instance variable and constructors
- It can have abstract methods (without implementation) and concrete methods (with impelementation)
- A class can extend only one abstract class
- An abstract class can't be instantiated directly

Purpose:
- To represent a base class with common functioanlity that the other similiar purpose classes can inherit
- To provide a common implementation to multiple related classes
- A class can extend only one abstract class.

**Interface**

Syntax:
- Defined using 'interface' keyword
- It can have only abstract methods, static methods, and default methods
- It can have constants (public static final) variables
- A class can implement multiple interfaces

Purpose:
- To Define a contract that must be followed by a class that implementing the interface
- To provide a common behavior to unrelated class
- When need multiple inheritance at the same class
- Multiple inheritance allows it to have multiple behaviors

#
## @FunctionalInterface

@FunctionalInterface annotation is used to indicate an interface to be a functional interface. A functional interhace contains exactly one abstract method, and these interfaces often used as the type of lambda expressions and method references.

```java
@FunctionalInterface
public interface MyFunctionalInterface {
    void myMethod();
}

public class FunctionalInterfaceExample {
    public static void main(String[] args) {
        MyFunctionalInterface instance = () -> System.out.println("Hello, Functional Interface!");
        instance.myMethod();  // Output: Hello, Functional Interface!
    }
}
```

The @FunctionalInterface annotation indicates that the interface MyFunctionalInterface is intended to be a functional interface. The interface MyFunctionalInterface is declared with a single abstract method myMethod() and makes it a functional interface.

In the main method, an instance of MyFunctionalInterface is created using a lambda expression, and the lambda expression provide the implementation for single abstract method.

#
## Implement class/interface

- [ATM Interface](code/src/main/java/org/example/assignment2/ATM.java)
- [ATMImpl Class](code/src/main/java/org/example/assignment2/ATMImpl.java)

- [Account Interface](code/src/main/java/org/example/assignment2/Account.java)
- [SavingAccount Class](code/src/main/java/org/example/assignment2/SavingAccount.java)
- [CurrentAccount Class](code/src/main/java/org/example/assignment2/CurrentAccount.java)
