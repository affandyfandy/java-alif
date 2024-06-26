package org.example;

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

    public static void modifyObject(MyClass x) {
        x.value = 10;
        x.attr = "Hello";
    }
}

class MyClass {
    int value;
    String attr;
}