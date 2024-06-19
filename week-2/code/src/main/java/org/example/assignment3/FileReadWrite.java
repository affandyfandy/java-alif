package org.example.assignment3;

import java.io.*;

public class FileReadWrite {
    public static void readWriteFile() {
        String inputFileName = "src/main/java/org/example/assignment3/lab1/test1.txt";

        // Create a StringBuilder to store the file content
        StringBuilder content = new StringBuilder();

        // Try-with-resources statement to ensure the BufferedReader is closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;

            // Read each line from the file until the end
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the StringBuilder to a String and print to the console
        String result = content.toString();
        System.out.println(result); // Output: Hello, some text here

        String outputFileName = "src/main/java/org/example/assignment3/lab1/test2.txt";

        // Try-with-resources statement to ensure the BufferedWriter is closed automatically
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {

            // Write the result content to the file
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        readWriteFile();
    }
}
