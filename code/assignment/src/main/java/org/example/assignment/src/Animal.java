package org.example.assignment.src;

// Parent class
public class Animal {
    public void sound() {
        System.out.println("Animal sound");
    }

    public void eat() {
        System.out.println("Animal is eating.");
    }

    public static void main(String[] args) {
        Dog myDog = new Dog();
        DogOwner dogOwner = new DogOwner();
        dogOwner.feedAnimal(myDog); // Animal is eating.

        AnimalShelter animalShelter = new AnimalShelter();
        animalShelter.adopt(myDog); // You adopted an animal that says: Animal sound Dog sound
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

class DogOwner {
    public void feedAnimal(Dog dog){
        dog.eat();
    }
}

class AnimalShelter {
    public void adopt(Animal animal) {
        System.out.print("You adopted an animal that says: ");
        animal.sound();
    }
}