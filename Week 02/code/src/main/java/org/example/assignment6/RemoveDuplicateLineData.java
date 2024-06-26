package org.example.assignment6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicateLineData {
    public static void main(String[] args) {
        String inputFile = "src/main/java/org/example/assignment6/file/input.csv";
        String outputFile = "src/main/java/org/example/assignment6/file/output.csv";

        try {
            removeDuplicateLinesByKeyField(inputFile, outputFile);
            System.out.println("Duplicates removed successfully. Output file: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    public static void removeDuplicateLinesByKeyField(String inputFile, String outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IOException("Input file is empty");
            }

            // Write the header to the output file
            writer.write(headerLine);
            writer.newLine();

            int keyFieldIndex = 0; // ID column index as key

            // Read, filter, and collect unique lines based on the key field
            List<String> uniqueLines = reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.groupingBy(
                            line -> line.split(",")[keyFieldIndex],
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    lines -> lines.get(0) // keep only the first occurrence if duplicates exist
                            )
                    ))
                    .values()
                    .stream()
                    .toList();

            // Write the unique lines to the output file
            for (String line : uniqueLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}

