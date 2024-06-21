# Assignment 6

[<ins>`Assignment 6 Code`</ins>](code/src/main/java/org/example/assignment6)

## 6.1 When we use parallel stream? What is notice? Give some example?

[<ins>`Code - 6.1 Parallel Stream`</ins>](code/src/main/java/org/example/assignment6/ParallelStreamFilter.java)

`Parallel stream` is a feature that allows for parallel processing of data, enable to execute code in parallel on separate cores. When using `parallel stream`, it can be divide the code into multiple streams that are executed in parallel on separate cores, and the final result is the combination of the individual outcomes. Using multiple threads form `ForkJoinPool` to process the elements concurrently, and will improve the performance operations.

`ForkJoinPool` is an implementation of `ExecutorService` to handle a large number of small tasks, leading to better utilization of available CPU resources.

**When to Use `Parallel Streams`**:

- `Large Data Sets`
    When working with a large collections of data. By using `parallel streams`, it can significantly reduce the time required for processing.

- `Computationally Intensive Operations`
    When the operations are CPU-bound, that require a significant amount of computational resources, the `parallel streams` can improve the performance.

- `Independent Operations`
    When the operations performance on each element in the stream are independent of each other, and doesn't require `synchronization`.

**What is noticed about `Parallel Streams`**:

- `Thread Safety`
    Ensure the operations performed in parallel are thread-safe and doesn't cause a race conditions.

- `Order`
    Parallel processing may not maintain the order of elements, unless they are explicitly required and handled.

- `Performance`
    Even there is performance benefits, there are also has overhead of parallel processing.

- `Resource Management`
    Parallel streams utilize the commong ForkJoinPool, which can impact other parallel tasks in the application

**Example**:

```java
public static void main(String[] args) {
    FilterAndMap(List.of("grape", "apple", "cherry", "hip", "fig", "banana", "lime"));
}
```

Create a list of words as a `List of String`.

```java

public static void FilterAndMap(List<String> list) {
    // Filter and map the list
    List<String> result = list.parallelStream()
            .filter(word -> word.length() > 3)
            .map(String::toUpperCase)
            .toList();

    // Print the resulting list
    result.forEach(System.out::println);
}
```

The `list.parallelStream()` obtain a parallel stream in a list that is from the parameter. The following operations will be executed in parallel using the ForkJoinPool.

The `filter` method is used to retain only words that has length is greater that 3 characters. This operation will be performed concurrently on different segment of the list.

The `map` method is used to transform each remaining word to uppercase. Also this operation is executed in parallel.

The `collect` map is used to gather the processed elements to a new list. Ensures that the results are combined into a single list.

Print a result by `System.out.println();` to show the filtered and transformed words.

**Output**:

```
GRAPE
APPLE
CHERRY
BANANA
LIME
```

There's no words that has length of the character is less or equal than 3.

#
## 6.2 Remove all duplicate elements from a list of string using streams

[<ins>`Code - 6.2 Remove Duplicate Elements From List`</ins> ](code/src/main/java/org/example/assignment6/RemoveDuplicateElementsList.java)

Remove all duplicate elements from a list of string using Streams, by using the `distinct()` method of the `Stream` interface. This method will returning a stream consisting of the distinct elements that according to `Object.equals(Object)`.

**Implementation**:

```java
public static void main(String[] args) {
    RemoveDuplicates(List.of("apple", "banana", "apple", "cherry", "banana", "pear", "fig", "pear"));
}

public static void RemoveDuplicates(List<String> list) {
    // Remove duplicates from the list
    List<String> result = list.stream()
        .distinct()
        .toList();

    // Print the resulting list
    System.out.println(result);
}
```

First, `create` a list that contains duplicate elements like "apple", "banana", and "pear" appear more than once. Create a `stream` from that list of words. Then using the `distinct()` method that return a stream of the distict element of the original stream. Lastly collect the result into a new list.

**Output**:

```
[apple, banana, cherry, pear, fig]
```

The order of elements will be the same as the original list.

#
## 6.3 Remove lines which is duplicated data by key field

A program that reads data from a file, removes lines that duplicated key fields, and write the filtered unique lines to a new file. In this case, I'll use data from CSV fromat file, and the key field is column `"ID"`

[<ins>`Code - 6.3 Remove Duplicate Line By Key From File`</ins> ](code/src/main/java/org/example/assignment6/RemoveDuplicateLineData.java)

**Explanation**:

```java
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

    ... // Other code
    }

    ... // Other code
}
```

**Try-with-resources**: Using `try-with-resources` statements to ensure that the `BufferedReader` and `BufferedWriter` closed after the try block.

**Reading the Header Line**: The first line of the CSV file as the header, if file empty then `IOException` is thrown.

**Write Header to Output**: The header line written to the output file.

```java
public static void removeDuplicateLinesByKeyField(String inputFile, String outputFile) throws IOException {
    ...
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

    ...
}
```
**Reading and Filtering Lines**: The `lines()` method will returning a stream of lines from the file, and the `filter()` method will removing empty lines.

**Grouping Line by Key Field**: The `collect(Collectors.groupingBy)` method groups lines by the key field value, the key field index is 0 because the `"ID"` is in the first index.

**Collect First Occurence To Remove Duplicates**: for each group of lines with the same key field value `"ID"`, only the first occurence is kept using `Collectors.collectiongAndThen` to remove duplicates line that has the same key field.

**Write Unique Lines**: After no more duplicate lines, the frouped and filtered lines are collected into a list to the output file, followed by a newline.

**Input File**:
\
`input.csv`
```
ID,Name,Age,Email
1,John Doe,28,johndoe@example.com
2,Jane Smith,34,janesmith@example.com
3,Bob Johnson,45,bobjohnson@example.com
1,John Doe,28,johndoe@example.com
4,Alice Brown,30,alicebrown@example.com
3,Bob Johnson,45,bobjohnson@example.com
5,Charlie Black,29,charlieblack@example.com
```

\
`output.csv`
```
ID,Name,Age,Email
1,John Doe,28,johndoe@example.com
2,Jane Smith,34,janesmith@example.com
3,Bob Johnson,45,bobjohnson@example.com
4,Alice Brown,30,alicebrown@example.com
5,Charlie Black,29,charlieblack@example.com
```

There is no more duplicated line by key.

#
## 6.4 Count the number of string in a list that start with a specific letter using streams.

[<ins>`Code - 6.4 Count the number of strings that start with a specific letter`</ins> ](code/src/main/java/org/example/assignment6/CountStringsListStartingWith.java)


Count the number of strings in a list that start with a specific letter, by using streams. The `filter()` method retain only strings that start with the spesific letter, and the `count()` method to get the number of elements in the filtered stream.

**Implementation**:

`Input`
```java
List<String> strings = Arrays.asList("Red", "Green", "Blue", "Pink", "Brown");
String startingLetter = "G";
```

Initialize a list of strings, and will search a starting letter by `"G"`.

```java
public static int countStringsStartingWith(List<String> list, String startingLetter) {
    // Count the number of strings in the list that start with the given letter
    return (int) list.stream()
            .filter(word -> word.startsWith(startingLetter))
            .count();
}
```

**Stream**: The list is passing by parameter, and converts the list into a stream by `list.stream()`.

**Filter**: Uses the `filter(word -> word.startsWith(startingLetter))` method to keep only those strings that with the specified `startingLetter`.

**Count**: Uses the `count()` to count number of elements that found in the stream.

\
`Output`
```java
int count = countStringsStartingWith(strings, startingLetter);

System.out.println("Number of strings starting with " + startingLetter + ": " + count);
// Output: Number of strings starting with G: 1
```

#
## 6.5 Sort, find, and check from list of employees

Using `Streams` to a list of employees for complete the task, sorting names alphabetically, finding the employee with the maximum salary, and checking if any employee names match specific keywords.

[<ins>`Code - 6.5 Sort, find, and check from list of Employees`</ins> ](code/src/main/java/org/example/assignment6/EmployeeOperations.java)

**Input**:

```java
// List of employees
List<Employee> employees = Arrays.asList(
        new Employee(101, "Alice", 30, 50000),
        new Employee(102, "David", 40, 70000),
        new Employee(103, "John", 32, 55000),
        new Employee(104, "Eve", 32, 55000),
        new Employee(105, "Charlie", 28, 45000),
        new Employee(106, "Bob", 35, 60000),
        new Employee(107, "John Doe", 33, 65000)
);
```

Sample a list of employees to perform the operations

\
**Sort name in alphabetical, ascending using streams**

```java
// Main method
// Task 1: Sort name in alphabetical, ascending using streams
System.out.println("\nSorted names alphabetically:");
List<Employee> sortedEmployees = sortNameAlphabetically(employees);
sortedEmployees.forEach(System.out::println);
```

```java
public static List<Employee> sortNameAlphabetically(List<Employee> employees) {
    // Sort the list of employees by name alphabetically
    return employees.stream()
            .sorted(Comparator.comparing(Employee::getName))
            .toList();
}
```

Convert the list of employees to a stream using `stream()` method. Sort the stream elements based on the name by using `sorted()` method, and the `Comparator.comparing(Employee::getName)` specify the key for sorting that is the name of the employee. Collect the sorted back into a list by `toList()` method.

`Output`:
```
Sorted names alphabetically:
Employee{id=101, name='Alice', age=30, salary=50000}
Employee{id=106, name='Bob', age=35, salary=60000}
Employee{id=105, name='Charlie', age=28, salary=45000}
Employee{id=102, name='David', age=40, salary=70000}
Employee{id=104, name='Eve', age=32, salary=55000}
Employee{id=103, name='John', age=32, salary=55000}
Employee{id=107, name='John Doe', age=33, salary=65000}
```

\
**Find employee has max salary using streams**

```java
// Main method
// Task 2: Find employee has max salary using streams
List<Employee> maxSalaryEmployee = findMaxSalary(employees);

System.out.println("\nEmployee with max salary:");
maxSalaryEmployee.forEach(System.out::println);
```

```java
public static List<Employee> findMaxSalary(List<Employee> employees) {
    // Find the employee with the maximum salary
    Optional<Employee> maxSalaryEmployee = employees.stream()
        .max(Comparator.comparingInt(Employee::getSalary));

    return maxSalaryEmployee.map(Collections::singletonList).orElse(Collections.emptyList());
}
```

Using the `max()` method to find the employee with maximum salary. This method use a `Comparator` as an argument to compare the salaries of two employees, and comparing by the salary from `Employee::getSalary`. The `max()` method will return an `Optional<Employee>`, and then mapping into the list of employee again.

`Output`:
```
Employee with max salary:
Employee{id=102, name='David', age=40, salary=70000}
```

\
**Check any employee names match with specific keywords or not**

```java
// Main method
// Task 3: Check any employee names match with specific keywords or not
String keyword = "John";
List<Employee> matchingEmployees = findNameMatchingKeyword(employees, keyword);
if (!matchingEmployees.isEmpty()) {
    System.out.println("\nThere is an employee with name matching '" + keyword + "'.");
    matchingEmployees.forEach(System.out::println);
} else {
    System.out.println("\nNo employee found with name matching '" + keyword + "'.");
}
```

```java
public static List<Employee> findNameMatchingKeyword(List<Employee> employees, String keyword) {
    // Find employees whose names contain the given keyword
    return employees.stream()
            .filter(employee -> employee.getName().toLowerCase().contains(keyword.toLowerCase()))
            .toList();
}
```

Using the `stream()` method first to convert the list into a stream. Filter out the employees whoose names match or contain the given keyword by using `filter()` method. Lastly, collect the filtered employees into a list.

`Output`:
```
There is an employee with name matching 'John'.
Employee{id=103, name='John', age=32, salary=55000}
Employee{id=107, name='John Doe', age=33, salary=65000}
```

#
## 6.6 Convert list employees to map with ID as key

[<ins>`Code - 6.6 Convert list of employees to map`</ins> ](code/src/main/java/org/example/assignment6/ConvertListEmployeesToMap.java)

Converting a list of employees to a map with their ID as the key, and the Employee object as the value. Uses the `Collectors.toMap()` method.

**Implementation**:

```java
public static Map<Integer, Employee> convertToMap(List<Employee> employees) {
    // Convert the list of employees to a map with employee ID as the key
    // and the employee object as the value
    Map<Integer, Employee> employeeMap = employees.stream()
            .collect(Collectors.toMap(Employee::getId, employee -> employee));

    // Print the resulting map
    employeeMap.forEach((id, employee) -> System.out.println(id + ": " + employee));

    return employeeMap;
}
```

Firstly, the list of employees wil converted into a stream by `stream()` method and collect it by `collect()` method. The `Collectors.toMap()` collect the stream elements into a map. In that case, the argument `Employee::getId` specifies the key mapper for the map, extract the ID for each value as a key. The `employee -> employee` specifies the value mapper, map each `Employee` object as a value to the key.

Then, it will print to the console by `forEach` method to iterate over the entries of the map, and taking the id and employee object pair.

`Output`:
```
101: Employee{id=101, name='Alice', age=30, salary=50000}
102: Employee{id=102, name='Bob', age=35, salary=60000}
103: Employee{id=103, name='Charlie', age=28, salary=45000}
104: Employee{id=104, name='David', age=40, salary=70000}
105: Employee{id=105, name='Eve', age=32, salary=55000}
```