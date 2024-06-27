package org.example.assignment5;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

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
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            Map<String, CSVRecord> uniqueEntries = new LinkedHashMap<>(); // Use LinkedHashMap to store entries

            // Iterate through each record in the CSV file
            for (CSVRecord record : records) {
                if (record.size() > keyFieldIndex) {
                    String key = record.get(keyFieldIndex).trim(); // Extract key field value
                    // Store in map to keep unique entries based on the key field
                    if (!uniqueEntries.containsKey(key)) {
                        uniqueEntries.put(key, record);
                    }
                }
            }
            reader.close();

            // Write unique entries to output CSV file
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

            for (CSVRecord record : uniqueEntries.values()) {
                csvPrinter.printRecord(record);
            }

            csvPrinter.close();
            writer.close();

            System.out.println("Unique entries without duplicates based on key field written to " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

