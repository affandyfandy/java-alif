# Value Types and Reference Types

## Compare: Value types and reference types. Explain and example. When passing as parameters to functions

#
### Value Types

**Value types** in Java are the primitive data types, namely byte, short, int, long, float, double, char, and Boolean. Value types store values directly and stored in the stack memory. When assigning a value type to a variable, it directly manipulates its value.

```java
int number1 = 10;
int number2 = number1;
number2 = 20;
System.out.println(number1); // Output: 10
System.out.println(number2); // Output: 20
```

The variable number1 and number2 are independent of each other after the assignment. When there is a change in number2, it does not affect the number1.

When pass a value type as parameters to function, a copy of the value is passed. Any modification to the parameter inside the function does not affect the original variable.

```java
public static void main(String[] args) {
    int number3 = 10;
    modifyValue(number3);
    System.out.println(number3); // Output: 10
}

public static void modifyValue(int number) {
    number = 20;
}
```

The value of number3 is copied to the parameter in the modifyValue function. Changing the number inside the method does not affect the original variable number3.

#
### Reference Types

**Reference types** in java store the memory address where the data is located rather than the data itself, or store references to object in the heap memory. Consists of data types arrays, strings, classes, interfaces, enums, and objects. When assigning a reference type to a variable, it will copying the reference, not the actual object.

```java
class MyClass {
    int number;
}

public class ReferencesTypes {
    public static void main(String[] args) {
        MyClass myClass1 = new MyClass();
        myClass1.number = 10;
        MyClass myClass2 = myClass1;
        myClass2.number = 20;
        System.out.println(myClass1.number); // 20
        System.out.println(myClass2.number); // 20
    }
}
```

myClass1 and myClass2 both reference the same ‘MyClass’ objects in the heap memory. Changing the object through myClass2 also changes it in myClass1.

When pass a reference type as a parameter to a function, a copy of the reference is passed. The function receives a reference to the same object in memory. Modifications to the object inside the function through reference will affect the original object.

```java
public static void main(String[] args) {
    MyClass myClass3 = new MyClass();
    myClass3.number = 10;
    modifyReference(myClass3);
    System.out.println(myClass3.number); // 30
}

public static void modifyReference(MyClass myClass) {
    myClass.number = 30;
}
```

The reference to myClass3 is copied to parameter in the modifyReference function. Modifying the parameter, that is the number changes the number property of the same object that myClass3 references, hence the change is reflected in the original object.

#
### Differences

- Value types store the actual data value directly in the variable, while reference types store a reference (or address) to the location of the actual data in memory.
- Value types are typically allocated on the stack or in the local memory of a method, while reference types are allocated on the heap memory.
- When passed to methods, value types are passed by value, make a copy of the value is passed, while reference types are passed by reference value, meaning a copy of the reference is passed.
- When assigning a value type to another variable, the actual value is copied, while when assigning a reference type to another variable, only the reference (address) is copied, not the actual object.