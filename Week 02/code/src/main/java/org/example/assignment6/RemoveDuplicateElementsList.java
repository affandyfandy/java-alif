package org.example.assignment6;

import java.util.List;

public class RemoveDuplicateElementsList {

    public static void main(String[] args) {
        RemoveDuplicates(List.of("apple", "banana", "apple", "cherry", "banana", "pear", "fig", "pear"));
    }

    public static void RemoveDuplicates(List<String> list) {
        // Remove duplicates from the list
        List<String> result = list.stream()
                .distinct()
                .toList();

        // Print the resulting list
        System.out.println(result);
    }
}
