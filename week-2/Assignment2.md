# Assignment 2

[<ins>`Assignment 2 Code`</ins>](code/src/main/java/org/example/assignment2/)


## 2.1 Q: Slide 10: what happen if implement 2 interface have same default method? How to solve? Demo in code.

[<ins>`Code - 2.1 MyClass`</ins>](code/src/main/java/org/example/assignment2/MyClass.java)

[<ins>`Code - 2.1 FirstInterface`</ins>](code/src/main/java/org/example/assignment2/FirstInterface.java)

[<ins>`Code - 2.1 SecondInterface`</ins>](code/src/main/java/org/example/assignment2/SecondInterface.java)

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
## 2.2 Q: Explain the difference between abstract class and interface (Syntax and Purpose)

In Java, abstract classes and interface used to achieve abstraction, but have different syntax and purposes.

**`Syntax`**

| Abstract Class | Interface |
| --- | --- |
| Defined using `abstract` keyword | Defined using `interface` keyword|
| It can have instance variable and constructors | It can have only abstract methods, static methods, and default methods |
| It can have abstract methods (without implementation) and concrete methods (with impelementation) | It can have constants (public static final) variables|
| A class can extend only one abstract class | A class can implement multiple interfaces
| Can't be instantiated directly | Can't be instantiated directly |

\
**`Purpose`**
| Abstract Class | Interface |
| --- | --- |
| To represent a base class with common functioanlity that the other similiar purpose classes can inherit | To Define a contract that must be followed by a class that implementing the interface |
| To provide a common implementation to multiple related classes | To provide a common behavior to unrelated class |
| A class can extend only one abstract class | When need multiple inheritance at the same class |
| | Multiple inheritance allows it to have multiple behaviors |

#
## 2.3 @FunctionalInterface

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
## 2.4 Implement class/interface

- [ATM Interface](code/src/main/java/org/example/assignment2/ATM.java)

The `ATM` interface defines a contract for ATM operations, including withdraw, deposit, queryBalance, login, and logout.

- [ATMImpl Class](code/src/main/java/org/example/assignment2/ATMImpl.java)

The `ATMImpl` class implements the `ATM` interface, managing ATM operations. Handle withdrawals, deposits, balance queries, login, and logout.

- [Account Interface](code/src/main/java/org/example/assignment2/Account.java)

The `Account` interface defines basic operations for bank account.

- [SavingAccount Class](code/src/main/java/org/example/assignment2/SavingAccount.java)

The `SavingAccount` class implements the `Account` interface, representing a saving account with methods to deposit, withdraw, and get the balance.

- [CurrentAccount Class](code/src/main/java/org/example/assignment2/CurrentAccount.java)

The `CurrentAccount` class implements the `Account` interface, representing a current account with methods for depositing, withdrawing with additional overdraft limit, and retrieving the balance.

*main method in ATMImpl Class
