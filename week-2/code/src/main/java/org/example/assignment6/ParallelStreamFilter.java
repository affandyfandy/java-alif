package org.example.assignment6;

import java.util.List;
import java.util.stream.Collectors;

public class ParallelStreamFilter {

    public static void main(String[] args) {
        FilterAndMap(List.of("grape", "apple", "cherry", "hip", "fig", "banana", "lime"));
    }

    public static void FilterAndMap(List<String> list) {
        // Filter and map the list
        List<String> result = list.parallelStream()
                .filter(word -> word.length() > 3)
                .map(String::toUpperCase)
                .toList();

        // Print the resulting list
        result.forEach(System.out::println);
    }
}
