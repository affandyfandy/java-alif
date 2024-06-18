package org.example.assignment2;

public interface FirstInterface {
    void firstMethod();

    default void log(String string) {
        System.out.println("This method is default implementation of FirstInterface " + string);
    }
}
