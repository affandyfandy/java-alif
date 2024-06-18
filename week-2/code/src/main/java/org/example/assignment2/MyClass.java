package org.example.assignment2;

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
