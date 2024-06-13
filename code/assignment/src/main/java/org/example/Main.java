package org.example;

import org.example.assignment.src.*;


public class Main {
    public static void main(String[] args) {
        // Get instance of the Singleton
        Singleton instance1 = Singleton.getInstance();

        // Get another instance of the Singleton
        Singleton instance2 = Singleton.getInstance();

        // Check if both instances are the same
        System.out.println("Are instances the same? " + (instance1 == instance2));
    }
}