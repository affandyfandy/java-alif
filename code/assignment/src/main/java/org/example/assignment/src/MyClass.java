package org.example.assignment.src;

public class MyClass {
    public static void main(String[] args) {
        MyObject myObj = new MyObject(10);
        System.out.println("Before reassignment: " + myObj.value); // Outputs: 10

        reassignObject(myObj);
        System.out.println("After reassignment: " + myObj.value); // Still outputs: 10
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