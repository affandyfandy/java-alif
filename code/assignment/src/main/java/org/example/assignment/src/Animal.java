package org.example.assignment.src;

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