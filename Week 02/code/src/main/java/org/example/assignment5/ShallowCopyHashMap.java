package org.example.assignment5;

import java.util.HashMap;

public class ShallowCopyHashMap {
    public static void main(String[] args) {
        // Create and populate the original HashMap
        HashMap<String, String> originalMap = new HashMap<>();
        originalMap.put("key1", "value1");
        originalMap.put("key2", "value2");
        originalMap.put("key3", "value3");

        // Get a shallow copy of the original HashMap
        @SuppressWarnings("unchecked")
        HashMap<String, String> shallowCopy = (HashMap<String, String>) originalMap.clone();

        // Print both HashMaps to show they contain the same data
        System.out.println("Original HashMap: " + originalMap);
        System.out.println("Shallow Copy HashMap: " + shallowCopy);

        // Modify the original HashMap
        originalMap.put("key4", "value4");

        // Print both HashMaps again to show they are different after the modification
        System.out.println("Modified Original HashMap: " + originalMap);
        System.out.println("Shallow Copy HashMap after original modification: " + shallowCopy);
    }
}
