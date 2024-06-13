package org.example.assignment;

import java.util.ArrayList;
import java.util.Arrays;

public class Assignment3 {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 4, 3, -6, 5, 4));
        System.out.println(findSecondLargestIndex(numbers)); // Output: [1, 5]
    }

    public static ArrayList<Integer> findSecondLargestIndex(ArrayList<Integer> numbers) {
        int largest = numbers.get(0);
        int secondLargest = numbers.get(0);
        int temp = 0;

        ArrayList<Integer> secondLargestIndexes = new ArrayList<>();

        for (int i = 0; i < numbers.size(); i++) {
            
            int number = numbers.get(i);
            if (number > largest) {
                secondLargest = largest;
                largest = number;
                temp = numbers.indexOf(secondLargest);
                secondLargestIndexes.clear();
            }

            if (number == secondLargest) {
                secondLargestIndexes.add(i);
            }
        }

        secondLargestIndexes.add(0,temp);

        return secondLargestIndexes;
    }
}
