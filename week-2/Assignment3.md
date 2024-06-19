# Assignment 3

[<ins>`Assignment 3 Code`</ins>](code/src/main/java/org/example/assignment3)

#
## 3.1 Q1 : Research and explain try-with-resources ?

The `try-with-resources` is a try statement that declares one or more resources. In this context, a resource is an object that must be closed after the program that use the resource is finished, the object implement `AutoCloseable` interface. The `try-with-resource` ensures that each resource is closed at the end of the statement.

```java
try (Scanner scanner = new Scanner(new File("sample.txt"))) {
    while (scanner.hasNext()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException e) {
    e.printStackTrace();
}
```

The `try-with-resources` read a file named "sample.txt" each line using Scanner class. Inside the try block, creates a Scanner object to read the line and print it to the console. As long as the scanner has more lines to read, it will prints each line to the console. The `try-with-resources` statement ensures that the Scanner is automatically closed when the try block is finished even there is exception appears.

#
## 3.2 Q2: Throw vs throws, give example

[<ins>`Code - 3.2 Throw vs throws`</ins>](code/src/main/java/org/example/assignment3/ThrowAndThrows.java)


The `throw` and `throws` is the concept of exception handling, where the `throw` keyword is used to explicitly trigger an exception from a method or a block of code, while the `throws` keyword is included in a method's signature to indicate that it may throw an exception.

\
**Throw**
```java
public class ThrowAndThrows {

    // Throw example
    public static void checkNum(int num) {
        if (num < 1) {
            throw new ArithmeticException("Number is negative, cannot calculate square");
        }
        else {
            System.out.println("Square of " + num + " is " + (num*num));
        }
    }

    public static void main(String[] args) {
        try {
            checkNum(-1);
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
```

The `checkNum` method uses the throw keyword to explicitly throw an `ArithmeticException` if the number in parameter is less than one. In the main method, the checkNum method is called with `-1`, causing the exception to be thrown.

\
**Throws**
```java
public class ThrowAndThrows {
    // Throws example
    // This method throws an ArithmeticException if n is 0
    public static int divideNum(int m, int n) throws ArithmeticException {
        return m / n;
    }

    public static void main(String[] args) {
        // Throws main method example
        try {
            System.out.println(divideNum(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }
}
```

The `divideNum` method is declared with `throws Arithmetic` that indicate it can throw this exception if an error occures, such as dividing by zero. When `divideNum` is called in the main method with `10` and `0`, it results an `ArithmeticException`. This exception is caught in the `try catch` block.

#
## 3.3 Lab 1 (try to use try-with-resources)

[<ins>`Code - 3.3 Lab 1`</ins>](code/src/main/java/org/example/assignment3/FileReadWrite.java)


[`test1.txt`](code/src/main/java/org/example/assignment3/lab1/test1.txt)
:
```
Hello, some text here
```

\
Read file test.txt and handle all exception, print text on file to console:
```java
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

```

The `readWriteFile` method reads from test1.txt using a `BufferedReader` within a `try-with-resources` block, ensuring the reader is closed automatically. It stores the file content in a StringBuilder and prints it to the console. Then, it writes this content to test2.txt using a  `try-with-resources` block that use `BufferedWriter` resource to write the contect to the text2.txt. If any IOException occurs during reading or writing, it catches and prints the stack trace. The main method calls readWriteFile to execute this process.

#
## 3.4 Lab 2

[<ins>`Code - 3.4 Lab 2`</ins>](code/src/main/java/org/example/assignment3/Lab2Exception.java)


Lab2Exception:
```java

// Custom exception class for Lab2
public class Lab2Exception extends Exception {

    /**
     * Constructor for Lab2Exception that accepts a custom error message
     * @param message the error message for exception
     */
    public Lab2Exception(String message) {
        super(message);
    }
}
```

 `Lab2Exception` is a custom exception class. It extends the built-in Exception class for handling exceptional conditions. The constructor `Lab2Exception(String message)` allows creating instances of this exception with a custom error message in parameter.

\
Menu:
```java
class Menu {

    public static void main(String[] args) {
        String[] menu = {"A", "B", "C", "D", "E"}; // Menu
        Scanner scanner = new Scanner(System.in);
        boolean valid = true;

        while (valid) { // Loop until user enters 0 (exit)

            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice < 0 || choice > 5) { // Validate input range
                    throw new Lab2Exception("Invalid choice");  // Throw custom exception for invalid input
                }

                if (choice == 0) {
                    valid = false;
                    continue;
                }

                System.out.println("You selected: " + menu[choice - 1]);
            } catch (Lab2Exception e) {
                System.err.println(e.getMessage()); // Catch and print custom exception message
                // Output: Invalid choice
            } catch (Exception e) {
                System.out.println("Unexpected error:" + e.getMessage());
            }
        }

        System.out.println("Program stopped");
        scanner.close();
    }
}
```

In this Menu class, the program implements a simple menu system where users can input a number corresponding to menu options A to E. If the user's input is out of range (1-5), a `Lab2Exception` is thrown with an error message and doesn't crash the application. This example demonstrates error handling with custom exceptions and general exceptions.

#
## 3.5 Lab 3

[<ins>`Code - 3.5 Lab 3`</ins>](code/src/main/java/org/example/assignment3/VowelException.java)


Vowel Exception:
```java
/**
 * Custom exception class for Vowel
 */
public class VowelException extends Exception{

    /**
     * Constructor for VowelException that accepts a custom error message
     * @param message the error message for exception
     */
    public VowelException(String message) {
        super(message);
    }
}
```

 `VowelException` is a custom exception class. It extends the built-in Exception class for handling exceptional conditions. The constructor `VowelException(String message)` allows creating instances of this exception with a custom error message in parameter.

\
Implementation:
 ```java
class Vowel {
    public static void vowelCheck(String input) throws VowelException {
        String vowel = "AIUEOaiueo"; // Define voewels
        boolean hasVowel = false;

        for (int i = 0; i < input.length(); i++) {
            if (vowel.indexOf(input.charAt(i)) != -1) { // Check if input contains vowel
                hasVowel = true;
                break;
            }
        }

        if (!hasVowel) { // Throw exception if input does not contain vowel
            throw new VowelException("Input does not contain any vowel");
        } else {
            System.out.println("Input "+ input + " contains vowel"); // Print message if input contains vowel
        }
    }

    public static void main(String[] args) {

        // This test case will throw exception
        try {
            vowelCheck("Hll"); // Test case without vowe;
        } catch (VowelException e) {
            System.err.println(e.getMessage());
        }

        // This test case will not throw exception
        try {
            vowelCheck("Hello"); // Output: Input Hello contains vowel
        } catch (VowelException e) {
            System.err.println(e.getMessage());
        }
    }
}
 ```
 In the Vowel class, the vowelCheck method checks whether a given string contains any vowels. Using a loop to iterate through each character of the input string and checks if it represents vowels in both uppercase and lowercase. If no vowels are found, it throws a `VowelException` with the message "Input does not contain any vowel". Otherwise, it prints a message indicating that vowels are present in the input string. In the main method, vowelCheck is called with different inputs inside try-catch blocks to demonstrate handling of `VowelException`. If the exception is caught, the error message is printed.