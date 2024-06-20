package org.example.assignment5;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListLab {
    public static void main(String[] args) {
        // Create a CopyOnWriteArrayList and add some elements
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        System.out.println("Initial list: " + list);

        // Thread to modify the list
        Thread writerThread = new Thread(() -> {
            list.set(1, "X"); // Modify element at index 1
            list.add("D");    // Add a new element
            System.out.println("Writer thread updated list: " + list);
        }, "Writer Thread");

        // Thread to iterate over the list
        Thread readerThread = new Thread(() -> {
            // Iterate over the list
            for (String s : list) {
                System.out.println("Reader thread reads: " + s);
                try {
                    Thread.sleep(50); // Sleep to simulate some work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Reader Thread");

        // Start both threads
        writerThread.start();
        readerThread.start();

        // Print the final state of the list
        System.out.println("Final list: " + list); // Output: Final list: [A, X, C, D]
    }
}
