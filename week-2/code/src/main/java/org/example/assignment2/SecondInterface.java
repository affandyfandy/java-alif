package org.example.assignment2;

public interface SecondInterface {
    void secondMethod();

    default void log(String str) {
        System.out.println("This method is default implementation of SecondInterface " + str);
    }
}
