package org.example.assignment3;

import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        String[] menu = {"A", "B", "C", "D", "E"};
        Scanner scanner = new Scanner(System.in);
        boolean valid = true;

        while (valid) {

            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice < 0 || choice > 5) {
                    throw new Lab2Exception("Invalid choice");
                }

                if (choice == 0) {
                    valid = false;
                    continue;
                }

                System.out.println("You selected: " + menu[choice - 1]);
            } catch (Lab2Exception e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error:" + e.getMessage());
            }
        }

        System.out.println("Program stopped");
        scanner.close();
    }
}
