package org.example.assignment5;

import java.io.*;
import java.util.*;

public class RemoveDuplicates {

    public static void main(String[] args) {

        String inputFile = "src/main/java/org/example/assignment5/file/input.csv";
        String outputFile = "src/main/java/org/example/assignment5/file/output.csv";
        int keyFieldIndex = 0; // Index of key field in CSV

        try {
            // Read input CSV file
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            Map<String, String> uniqueEntries = new LinkedHashMap<>(); // Use LinkedHashMap to store entries

            // Iterate through each line in the CSV file
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(","); // Assuming CSV fields are comma-separated

                // Skip header line if needed (remove if CSV has no header)
                if (fields.length > keyFieldIndex) {
                    String key = fields[keyFieldIndex].trim(); // Extract key field value
                    // Store in map to keep unique entries based on the key field
                    if (!uniqueEntries.containsKey(key)) {
                        uniqueEntries.put(key, line);
                    }
                }
            }
            reader.close();

            // Write unique entries to output CSV file
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (String entry : uniqueEntries.values()) {
                writer.write(entry);
                writer.newLine();
            }
            writer.close();

            System.out.println("Unique entries without duplicates based on key field written to " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

