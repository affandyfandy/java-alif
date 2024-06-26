package org.example.assignment3;

/**
 * Custom exception class for Vowel
 */
public class VowelException extends Exception{

    /**
     * Constructor for VowelException that accepts a custom error message
     * @param message the error message for exception
     */
    public VowelException(String message) {
        super(message);
    }
}

class Vowel {
    public static void vowelCheck(String input) throws VowelException {
        String vowel = "AIUEOaiueo"; // Define voewels
        boolean hasVowel = false;

        for (int i = 0; i < input.length(); i++) {
            if (vowel.indexOf(input.charAt(i)) != -1) { // Check if input contains vowel
                hasVowel = true;
                break;
            }
        }

        if (!hasVowel) { // Throw exception if input does not contain vowel
            throw new VowelException("Input does not contain any vowel");
        } else {
            System.out.println("Input "+ input + " contains vowel"); // Print message if input contains vowel
        }
    }

    public static void main(String[] args) {

        // This test case will throw exception
        try {
            vowelCheck("Hll"); // Test case without vowe;
        } catch (VowelException e) {
            System.err.println(e.getMessage());
        }

        // This test case will not throw exception
        try {
            vowelCheck("Hello"); // Output: Input Hello contains vowel
        } catch (VowelException e) {
            System.err.println(e.getMessage());
        }
    }
}
