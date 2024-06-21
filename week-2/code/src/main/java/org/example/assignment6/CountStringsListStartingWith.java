package org.example.assignment6;

import java.util.Arrays;
import java.util.List;

public class CountStringsListStartingWith {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("Red", "Green", "Blue", "Pink", "Brown");
        String startingLetter = "G";

        int count = countStringsStartingWith(strings, startingLetter);
        System.out.println("Number of strings starting with " + startingLetter + ": " + count);
        // Output: Number of strings starting with G: 1
    }

    public static int countStringsStartingWith(List<String> list, String startingLetter) {
        // Count the number of strings in the list that start with the given letter
        return (int) list.stream()
                .filter(word -> word.startsWith(startingLetter))
                .count();
    }
}

