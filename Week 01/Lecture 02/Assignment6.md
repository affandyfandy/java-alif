# Explain previous slice as slice 6 (stack & heap).

Memory allocation is divided into stack memory and heap memory. Stack memory is used for storing method call frames and local variables, with each thread having its own stack. Heap memory, on the other hand, is where objects are allocated, particularly those created using the new keyword.

#
## Modify the object that the reference points to

```java
public class Main {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.value = 5;
        obj.attr = "World";

        modifyObject(obj);
        System.out.println("obj.value after modifyObject: " + obj.value + ", " + obj.attr);
    }

    public static void modifyObject(MyClass x) {
        x.value = 10;
        x.attr = "Hello";
    }
}

class MyClass {
    int value;
    String attr;
}
```

### Explanation

```java
public class Main {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.value = 5;
        obj.attr = "World";
        // ...
    }
}

class MyClass {
    int value;
    String attr;
}
```

The obj is a referece types variable of type MyClass, and the variable is stored on the stack. 'new MyClass()' creates an instance of MyClass on the heap.  The obj holds the reference of the instance. The value field is set to 5 and the attr field is set to "World"

```java
public static void main(String[] args) {
    // ...
    modifyObject(obj);
    // ...
}

public static void modifyObject(MyClass x) {
    x.value = 10;
    x.attr = "Hello";
}
```

Before calling the modifyObject(), the obj.value is 5 and the obj.attr is "World". The obj reference is passed to the modifyObject(). The parameter that is x in modifyObject() holds the same reference as obj, pointing to the same MyClass instance on the heap.

The fields value and attr of MyClass can be accessed and modified via any reference pointing to this object. x.value = 10 update the value field of the MyClass instance on the heap to 10, and x.attr = "Hello" update the attr field to "Hello". The x in parameter point to the same object of obj on the heap, the changes are reflected in the object referenced.

```java
System.out.println("obj.value after modifyObject: " + obj.value + ", " + obj.attr);
```

Prints the updated value of the instance, and the output will be
"obj.value after modifyObject: 10, Hello"

#
## Cannot change the reference itself to point to a different object

```java
public class Main {
    public static void main(String[] args) {
        MyClass obj = new MyClass();
        obj.value = 5;
        obj.attr = "World";

        changeReference(obj);
        System.out.println("obj.value after changeReference: " + obj.value + ", " + obj.attr);
        // Output: obj.value after changeReference: 5, World
    }

    public static void changeReference(MyClass x) {
        x = new MyClass();
        x.value = 10;
        x.attr = "Hello";
    }
}

class MyClass {
    int value;
    String attr;
}
```

### Explanation

```java
public static void main(String[] args) {
    // ...
    changeReference(obj);
    // ...
}

public static void changeReference(MyClass x) {
    x = new MyClass();
    x.value = 10;
    x.attr = "Hello";
}
```

Different from the previous one, the methods used here are changeReference() that has MyClass as parameters. The reference obj is passed to the method changeReference(). The parameter x in changeReference() holds the same reference as obj, and pointing to the same instance on the heap.

The changeReference() creates a new instance of MyClass on the heap in parameter x. Now x points to the new instance. The x variable sets the value filed of the new instance to 10, and sets the attr field to "Hello".

```java
    System.out.println("obj.value after changeReference: " + obj.value + ", " + obj.attr);
```

The original obj in the main method still points to the original MyClass instance with value = 5 and attr = "World", because the x in the changeReference() points to the another (new) instance of MyClass. And the output will be
"obj.value after changeReference: 5, World"