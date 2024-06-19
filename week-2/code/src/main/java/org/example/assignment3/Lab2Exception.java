package org.example.assignment3;

import java.util.Scanner;

/**
 * Custom exception class for Lab2
 */
public class Lab2Exception extends Exception {

    /**
     * Constructor for Lab2Exception that accepts a custom error message
     * @param message the error message for exception
     */
    public Lab2Exception(String message) {
        super(message);
    }
}

class Menu {

    public static void main(String[] args) {
        String[] menu = {"A", "B", "C", "D", "E"}; // Menu
        Scanner scanner = new Scanner(System.in);
        boolean valid = true;

        while (valid) { // Loop until user enters 0 (exit)

            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice < 0 || choice > 5) { // Validate input range
                    throw new Lab2Exception("Invalid choice");  // Throw custom exception for invalid input
                }

                if (choice == 0) {
                    valid = false;
                    continue;
                }

                System.out.println("You selected: " + menu[choice - 1]);
            } catch (Lab2Exception e) {
                System.err.println(e.getMessage()); // Catch and print custom exception message
                // Output: Invalid choice
            } catch (Exception e) {
                System.out.println("Unexpected error:" + e.getMessage());
            }
        }

        System.out.println("Program stopped");
        scanner.close();
    }
}

