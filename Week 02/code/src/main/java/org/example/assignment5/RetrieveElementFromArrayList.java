package org.example.assignment5;

import java.util.ArrayList;

public class RetrieveElementFromArrayList {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        // Add elements to the ArrayList
        arrayList.add(10);
        arrayList.add(20);
        arrayList.add(30);
        arrayList.add(40);
        arrayList.add(50);

        // Specified index
        int index = 3;

        try {
            // Retrieve element from specified index
            int element = arrayList.get(index);

            // Print the retrieved element
            System.out.println("Element at index " + index + " is: " + element);
        } catch (IndexOutOfBoundsException e) {

            // Print the exception message if index is out of bounds
            System.out.println("Index " + index + " is out of bounds for ArrayList");
        }
    }
}

