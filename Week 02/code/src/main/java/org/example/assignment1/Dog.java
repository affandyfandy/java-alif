package org.example.assignment1;

public class Dog {
    private String color;
    private String name;
    private String breed;

    public Dog(String color, String name, String breed) {
        this.color = color;
        this.name = name;
        this.breed = breed;
    }

    public void wagTail() {
        System.out.println(name + " is wagging its tail.");
    }

    public void bark() {
        System.out.println(name + " is barking.");
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }
}