# Assignment 7

[<ins>`Assignment 7 Code`</ins>](../code/src/main/java/org/example/assignment7)

## 7.1 Remove duplicated items for any object and any duplicated field

[<ins>Code - 7.1 Remove duplicated items</ins>](../code/src/main/java/org/example/assignment7/RemoveDuplicateAny.java)

Using generic approach to remove duplicated items from a collection based and on a specific field. The `Collectors.toMap()` method will ensure that there are no duplicate keys based on the specific field.

**Implementation**:

```java
public static <T, R> List<T> removeDuplicates(List<T> list, Function<? super T, ? extends R> keyExtractor) {
    return new ArrayList<>(list.stream()
        .collect(Collectors.toMap(
            keyExtractor, // Specifies the key
            Function.identity(), // Value itself
            (existing, replacement) -> existing // Merge function to handle duplicates
        ))
        .values());
}
```

The `stream()` method will convert the list from the parameter into a stream, then collect the stream elements to a map by `collect(Collectors.toMap())` method. In the `toMap()` parameter, it specifies the key for the map, that passing through from the parameter. The `Function.identity()` will specifies the value for the map, that is the object itself. To handle duplicates, using `(existing, replacement) -> existing` method. Then, get the value from the map and coonvert it again into a list by `values()` method.

```java
public static void main(String[] args) {
    List<Employee> employees = Arrays.asList(
            new Employee(101, "Alice", 30, 50000),
            new Employee(102, "Bob", 35, 60000),
            new Employee(103, "Charlie", 28, 45000),
            new Employee(104, "David", 40, 70000),
            new Employee(105, "Eve", 32, 55000),
            new Employee(102, "Bob Duplicate", 36, 65000),
            new Employee(106, "Alice", 31, 55000)
    );

    // Remove duplicates based on employee ID
    List<Employee> uniqueEmployeesById = removeDuplicates(employees, Employee::getId);
    System.out.println("Unique employees by ID:");
    uniqueEmployeesById.forEach(System.out::println);

    // Remove duplicates based on employee name
    List<Employee> uniqueEmployeesByName = removeDuplicates(employees, Employee::getName);
    System.out.println("\nUnique employees by name:");
    uniqueEmployeesByName.forEach(System.out::println);
}
```

Based on the list of employees that has been created. First case is removing the duplicates based on employee ID, the `removeDuplicates` method take a list of `Employee` object as an input. The `Employee::getId` is used for specifies the field that will be checked. Lastly, it will loop in the employee list that unique by `Id`. The output for the first case is:

```
Unique employees by ID:
Employee{id=101, name='Alice', age=30, salary=50000}
Employee{id=102, name='Bob', age=35, salary=60000}
Employee{id=103, name='Charlie', age=28, salary=45000}
Employee{id=104, name='David', age=40, salary=70000}
Employee{id=105, name='Eve', age=32, salary=55000}
Employee{id=106, name='Alice', age=31, salary=55000}
```

#
## 7.2 Demo: Using Wildcards With Generics

[<ins>Code - 7.2 Wildcards Demo</ins>](../code/src/main/java/org/example/assignment7/WildcardDemo.java)


`Wildcards` in generics are used to create flexibility and reusability code by allowing parameters by various types to maintain type safety.

**Types of wildcards**:
1. `Unbounded Wildcards`: Using `<?>` to match any type.
2. `Upper Bounded Wildcards`: Using `<? extends Type>` to match any type that is subclass of `Type`. (Inclusive)
3. `Lower Bounded Wildcards`: Using `<? super Type>` to match any type that is a superclass of `Type`. (Inclusive)

**Demo**:

\
`Unbounded Wildcards`
```java
public static void main(String[] args) {
    // Unbounded wildcard
    List<String> stringList = new ArrayList<>();
    stringList.add("Hello");
    stringList.add("World");
    printList(stringList);
}
```
```java
// Unbounded wildcard
public static void printList(List<?> list) {
    for (Object elem : list) {
        System.out.print(elem + " ");
    }
    System.out.println();
}
```

This `printList()` will prints all element of a list. In the parameter, there is an `unbounded wildcard` that is `<?>` keyword to accept a list of any type.

\
`Upper Bounded Wildcards`
```java
// Upper bounded wildcard
public static void main(String[] args) {
    List<Integer> integerList = new ArrayList<>();
    integerList.add(1);
    integerList.add(2);
    integerList.add(3);
    printNumbers(integerList);
}
```
```java
// Upper bounded wildcard
public static void printNumbers(List<? extends Number> list) {
    for (Number num : list) {
        System.out.print(num + " ");
    }
    System.out.println();
}
```

This `printNumbers()` method willprints all elements of a list that contains `Number` objects or its subclasses. In the parameter, `<? extends Number>` is an upper bounded wildcard to accept a list of `Number` or any subclass of the `Number`.

\
`Lower Bounded Wildcards`

```java
public static void main(String[] args) {
    // Lower bounded wildcard
    List<Number> numberList = new ArrayList<>();
    addIntegers(numberList);
    printList(numberList);
}
```
```java
// Lower bounded wildcard
public static void addIntegers(List<? super Integer> list) {
    list.add(10);
    list.add(20);
    list.add(30);
}
```

This methods add `Integer` values to a list. The `List<? super Integer> list` in the parameter use a lower bounded wildcard to accept a list of `Integer` or any superclass of `Integer`.

**Output**:
```
Unbounded wildcard demo:
Hello World 

Upper bounded wildcard demo:
1 2 3 
1.1 2.2 3.3 

Lower bounded wildcard demo:
10 20 30 
```

#
## 7.3  Sort by any field and Find item has max value of any field from list of any object.

[<ins>Code - 7.3 Sort and find any field from list of any field</ins>](../code/src/main/java/org/example/assignment7/SortAndFindAny.java)

To do the sort and find max value, it can be achieve by a `sort()` method and `max()` method. Also, to achieve any field and object, use a generic method to do.

**Sort by any field**:
```java
// Method to sort a list by any field
public static <T> void sortByField(List<T> list, Comparator<? super T> comparator) {
    list.sort(comparator);
}
```

This method sorts a list of any type by any field. In parameter, the any type `T` by any field and `List<T> list` accept a list of any type. Using `sort()` method to sort a list, and the parameter is to specify the field, using `comparator` that define a field to sort.

```java
public static void main(String[] args) {
    // Sorting by age
    sortByField(employees, Comparator.comparingInt(Employee::getAge));
    System.out.println("\nSorted by age: " + employees);

    // Sorting by salary
    sortByField(employees, Comparator.comparingInt(Employee::getSalary));
    System.out.println("\nSorted by salary: " + employees);
}
```

The first case is sorting by `age`, the `Comparator` in the argument is pointing the age `Employee::getAge`. And the second case is sorting by `salary` field.

`Output`:
```
Sorted by age: [Employee{id=102, name='Bob', age=25, salary=60000}, Employee{id=101, name='Alice', age=30, salary=50000}, Employee{id=103, name='Charlie', age=35, salary=55000}]

Sorted by salary: [Employee{id=101, name='Alice', age=30, salary=50000}, Employee{id=103, name='Charlie', age=35, salary=55000}, Employee{id=102, name='Bob', age=25, salary=60000}]
```

\
**Find item has max value of any field**:

```java
// Method to find the item with the max value of any field
public static <T> Optional<T> findMaxByField(List<T> list, Comparator<? super T> comparator) {
    return list.stream().max(comparator);
}
```

This method will finds the item with the maximum value for any field in a list of any type. In parameter, the any type `T` by any field and `List<T> list` accept a list of any type. Using the `max()` method that returning a maximum element according to the specified comparator.

```java
public static void main(String[] args) {
    // Finding the employee with the maximum age
    Optional<Employee> maxAgeEmployee = findMaxByField(employees, Comparator.comparingInt(Employee::getAge));
    maxAgeEmployee.ifPresent(employee -> System.out.println("\nEmployee with max age: " + employee));

    // Finding the employee with the maximum salary
    Optional<Employee> maxSalaryEmployee = findMaxByField(employees, Comparator.comparingInt(Employee::getSalary));
    maxSalaryEmployee.ifPresent(employee -> System.out.println("\nEmployee with max salary: " + employee));
}
```

The first case is finding a maximum `age` from the list that can be seen the comparator is `Employee::getAge`. And the second case is finding a maximum `salary` from the list.

`Output`:
```
Employee with max age: Employee{id=103, name='Charlie', age=35, salary=55000}

Employee with max salary: Employee{id=102, name='Bob', age=25, salary=60000}
```

#
## 7.4 Convert list any object to map with any key field

[<ins>Code - 7/4 Convert list any object to map</ins>](../code/src/main/java/org/example/assignment7/ConvertListToMap.java)

Using `generics`, `streams`, and `collectors` to convert a list of any type of objects into a map using a specified key field.

**Implementation**:

```java
public class ConvertListToMap {

    public static void main(String[] args) {
        // List of employees
        List<Employee> employees = List.of(
                new Employee(101, "Alice", 30, 50000),
                new Employee(102, "Bob", 35, 60000),
                new Employee(103, "Charlie", 28, 45000),
                new Employee(104, "David", 40, 70000),
                new Employee(105, "Eve", 32, 55000)
        );

        // Convert list of employees to a map with name as the key
        Map<Integer, Employee> employeeMapByName = listToMap(employees, Employee::getId);
        System.out.println("Employee Map by ID:");
        employeeMapByName.forEach((name, employee) -> System.out.println(name + " -> " + employee));
    }

    // Method to convert list of any object to map with any key field
    public static <T, K> Map<K, T> listToMap(List<T> list, Function<? super T, ? extends K> keyExtractor) {
        return list.stream()
            .collect(Collectors.toMap(
                keyExtractor, // Key extractor function
                Function.identity() // Value mapper
            ));
    }
}
```

This method converts a list of object of type `T` generics to a map where the key is of type `K`, using the specified key. Using `streams` to convert the list of any objects into a map. The key `extractor` function in the parameter extract the key of type `K` from each object `T`. The value mapper that is `Function.identity()` maps each object to itself.

**Output**:

```
Employee Map by ID:
101 -> Employee{id=101, name='Alice', age=30, salary=50000}
102 -> Employee{id=102, name='Bob', age=35, salary=60000}
103 -> Employee{id=103, name='Charlie', age=28, salary=45000}
104 -> Employee{id=104, name='David', age=40, salary=70000}
105 -> Employee{id=105, name='Eve', age=32, salary=55000}
```

#
## 7.5 Design Class generic for paging data (any Object). Demo the use.

[<ins>Code - 7.5 Class generic for paging data</ins>](../code/src/main/java/org/example/assignment7/PagingData.java)

Creating a `generic` class for paging data to handle paging of any type of objects. Including objects, page size, current page, and navigate.

**Implementation**:
```java
public class PagingData<T> {
    private List<T> dataList;
    private int pageSize;
    private int currentPage;

    public PagingData(List<T> dataList, int pageSize) {
        this.dataList = dataList;
        this.pageSize = pageSize;
        this.currentPage = 1;
    }

    public List<T> getCurrentPageData() {
        int fromIndex = (currentPage - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, dataList.size());
        return dataList.subList(fromIndex, toIndex);
    }

    public void nextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
        }
    }

    public void previousPage() {
        if (currentPage > 1) {
            currentPage--;
        }
    }

    public List<T> getAllData() {
        return dataList;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPageNumber() {
        return currentPage;
    }


    public int getTotalPages() {
        return (int) Math.ceil((double) dataList.size() / pageSize);
    }
}
```

In `PagingData` class, there is `dataList` that contain list of objects to paginate, `pageSize` contain number of items per page, and `currentPage`.

The `getCurrentPageData()` retrieves the list of objects for current page. `nextPage()` and `previousPage()` moves to the other side of page. The `getTotalPages()` return a total number of page based on the list and page. And other method to get the spesific field.

```java
public static void main(String[] args) {
    // Sample list of employees
    List<Employee> employees = Arrays.asList(
            new Employee(101, "Harry", 41, 70000),
            new Employee(102, "Ivy", 31, 55000),
            new Employee(103, "Jack", 34, 60000),
            new Employee(104, "Kevin", 36, 65000),
            new Employee(105, "Alice", 31, 55000)
    );

    // Create PagingData instance for employees
    PagingData<Employee> pagingData = new PagingData<>(employees, 2);

    // Display current page data
    System.out.println("Page " + pagingData.getCurrentPageNumber() + ":");
    pagingData.getCurrentPageData().forEach(System.out::println);

    // Move to next page
    pagingData.nextPage();
    System.out.println("\nPage " + pagingData.getCurrentPageNumber() + ":");
    pagingData.getCurrentPageData().forEach(System.out::println);

    // Move to previous page
    pagingData.previousPage();
    System.out.println("\nPage " + pagingData.getCurrentPageNumber() + ":");
    pagingData.getCurrentPageData().forEach(System.out::println);

    // Display total pages
    System.out.println("\nTotal Pages: " + pagingData.getTotalPages());
}
```

Create a list of `Employee` objects, and the `PagingData` handle a page size of 2 data. Demonstrating `nextPage()` and `previousPage()` method, and print the page.

**Output**:
```
Page 1:
Employee{id=101, name='Harry', age=41, salary=70000}
Employee{id=102, name='Ivy', age=31, salary=55000}

Page 2:
Employee{id=103, name='Jack', age=34, salary=60000}
Employee{id=104, name='Kevin', age=36, salary=65000}

Page 1:
Employee{id=101, name='Harry', age=41, salary=70000}
Employee{id=102, name='Ivy', age=31, salary=55000}

Total Pages: 3
```