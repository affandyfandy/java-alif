package org.example.assignment3;

public class Vowel {
    public static void vowelCheck(String input) throws VowelException {
        String vowel = "AIUEOaiueo";
        boolean hasVowel = false;

        for (int i = 0; i < input.length(); i++) {
            if (vowel.indexOf(input.charAt(i)) != -1) {
                hasVowel = true;
                break;
            }
        }

        if (!hasVowel) {
            throw new VowelException("Input does not contain any vowel");
        } else {
            System.out.println("Input "+ input + " contains vowel");
        }
    }

    public static void main(String[] args) {
        try {
            vowelCheck("Hll");
        } catch (VowelException e) {
            System.err.println(e.getMessage());
        }

        try {
            vowelCheck("Hello");
        } catch (VowelException e) {
            System.err.println(e.getMessage());
        }
    }
}
