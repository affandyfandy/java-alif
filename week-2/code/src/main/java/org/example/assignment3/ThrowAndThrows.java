package org.example.assignment3;

public class ThrowAndThrows {

    // Throw example
    public static void checkNum(int num) {
        if (num < 1) {
            throw new ArithmeticException("Number is negative, cannot calculate square");
        }
        else {
            System.out.println("Square of " + num + " is " + (num*num));
        }
    }

    // Throws example
    // This method throws an ArithmeticException if n is 0
    public static int divideNum(int m, int n) throws ArithmeticException {
        return m / n;
    }

    public static void main(String[] args) {

        // Throw main method example
        try {
            checkNum(-1);
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // Throws main method example
        try {
            System.out.println(divideNum(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
