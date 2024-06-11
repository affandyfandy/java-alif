package org.example.assignment.src;

public class ValueTypes {
    public static void main(String[] args) {
        int number1 = 10;
        int number2 = number1;
        number2 = 20;
        System.out.println(number1); // Output: 10
        System.out.println(number2); // Output: 20

        int number3 = 10;
        modifyValue(number3);
        System.out.println(number3); // Output: 10
    }

    public static void modifyValue(int number) {
        number = 20;
    }
}
