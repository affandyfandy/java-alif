package org.example.assignment5;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrencyHashMap {
    public static void main(String[] args) {
        // Create a ConcurrentHashMap
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 1); // Add some elements
        map.put("B", 2);
        map.put("C", 3);
        map.put("D", 4);

        System.out.println("Initial map: " + map);

        // Thread to modify the map
        Thread writerThread = new Thread(() -> {
            map.put("B", 35); // Modify an existing element, B -> 35
            map.put("E", 5);  // Add a new element, E -> 5
            System.out.println("Updated map: " + map);
        }, "Writer Thread");

        // Thread to read from the map
        Thread readerThread = new Thread(() -> {
            // Iterate over the map entries
            map.forEach((key, value) -> {
                System.out.println("Map reads: " + key + " -> " + value);
                try {
                    Thread.sleep(50); // Sleep to simulate some work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }, "Reader Thread");

        // Start both threads
        writerThread.start();
        readerThread.start();

        // Final state of the map
        System.out.println("Final map: " + map);
    }
}
