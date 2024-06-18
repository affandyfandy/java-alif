package org.example.assignment1;

import org.example.assignment2.*;

public class MyClass implements FirstInterface, SecondInterface {
    public void firstMethod() {
        System.out.println("This is first method");
    }

    public void secondMethod() {
        System.out.println("This is second method");
    }

    @Override
    public void log(String string) {
        FirstInterface.super.log(string);
        SecondInterface.super.log(string);
    }

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        myClass.firstMethod();
        myClass.secondMethod();
        myClass.log("Hello");
    }
}
