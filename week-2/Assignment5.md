# Assignment 5

[<ins>`Assignment 5 Code`</ins>](code/src/main/java/org/example/assignment5)

## 5.1 Collection Comparison

The comparison of `ArrayList` vs `LinkedList`

**ArrayList vs LinkedList**

| Criteria | ArrayList | LinkedList |
|---|---|---|
| **Order** | Maintains insertion order, the elements are stored in the order they are added | Maintains insertion order, the elements are doubly linked |
| **Null element** | Allows null | Allows null |
| **Performance** | Faster for random access (get and set operations) | Faster for add and remove operations, especially in the middle of the list |
| **Synchronized** | Not synchronized by default, but can be synchronize externally using `Collections.synchronizedList(new ArrayList<>())` | Not synchronized by default, but can be synchronize externally using `Collections.synchronizedList(new LinkedList<>())` |
| **Fail-Fast/Fail-Safe** | Fail-Fast iterators | Fail-Fast iterators |
| **When to Use** | When frequent access or traversal using `get` and `set` methods are required, also where the elements are added or removed at the end of the list | When frequent insertions and deletions are required in the middle of the list, also when implementing queues |


---

**HashSet vs TreeSet vs LinkedHashSet**

| Criteria | HashSet | TreeSet | LinkedHashSet |
|---|---|---|---|
| **Order** | Does not maintain any order (neither using insertion nor natural) | Maintains elements in sorted order (natural order or comparator provided) | Maintains insertion order |
| **Null element** | Allows one `null` element | Doesn't allow `null` elements because uses a natural order or comparator | Allows one `null` element |
| **Performance** | Average `O(1)` time complexity for add, remove, and contains operation | `O(log n)` time complexity for add, remove, and contains operations | Average `O(1)` time complexity for add, remove, and contains operation, but slightly slower than HashSet due to maintaining insertion order |
| **Synchronized** | Not synchronized by default, but can be synchronize externally using `Collections.synchronizedSet` | Not synchronized by default, but can be synchronize externally using `Collections.synchronizedSet` | Not synchronized by default, but can be synchronize externally using `Collections.synchronizedSet` |
| **Fail-Fast/Fail-Safe** | Fail-Fast iterators | Fail-Fast iterators | Fail-Fast iterators |
| **When to Use** | When need a fast an unordered collection with unique elements | When need an elements in sorted order that require sorted data | When need an elements in the order they were inserted, suitable for require iteration in the order of insertion |

#
## 5.2 Write a Java program to retrieve an element (at a specified index) from a given array list.

[<ins>`Code - 5.2 Retrieve an alement from ArrayList`</ins>](code/src/main/java/org/example/assignment5/RetrieveElementFromArrayList.java)

Retrieve an element from a specific index in an ArrayList by using the `get()` method provided by the ArrayList class. Belo is the implementation.

```java
import java.util.ArrayList;

public class RetrieveElementFromArrayList {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        // Add elements to the ArrayList
        arrayList.add(10);
        arrayList.add(20);
        arrayList.add(30);
        arrayList.add(40);
        arrayList.add(50);

        // Specified index
        int index = 3;

        try {
            // Retrieve element from specified index
            int element = arrayList.get(index);

            // Print the retrieved element
            System.out.println("Element at index " + index + " is: " + element);
        } catch (IndexOutOfBoundsException e) {

            // Print the exception message if index is out of bounds
            System.out.println("Index " + index + " is out of bounds for ArrayList");
        }
    }
}
```

Based on that, there is several step to retrieving an element from a specific index in an ArrayList:

1. Create an `ArrayList` named arrayList to store integers using `ArrayList<Integer> arrayList = new ArrayList<>();`
2. Add elements (10, 20, 30, 40, 50) to the arrayList using the `add()` method `arrayList.add(...)`
3. To retrieve an element at a specific index, use the `get(index)` method of the ArrayList class arrayList.`get(index)`. The index is the position of the element to retrieve (in this implementation, index = 3).
4. Ensure the index is within valid range of the ArrayList to prevents `IndexOutOfBoundsException`.

The output will be: `Element at index 3 is: 40`.

#
## 5.3 Remove lines which is duplicated data by 1 key field

[<ins>`Code - 5.3 Remove duplicated lines`</ins>](code/src/main/java/org/example/assignment5/RemoveDuplicates.java)

Removing lines that is duplicated data based on a key field from a CSV file and write the unique data to a new file by:

1. **Read the CSV File**: read the data from CSV file as an input
2. **Identify the Key Field**: determine the key field based column index in the CSV data, the key field is column ID
3. **Remove Duplicates**: using `HashSet` to track unique data based on the key field
4. **Write Unique Datas**: write the unique datas to a new CSV file.

### Implementation

The program containing:

1. **Read the input CSV file**:

```java
BufferedReader reader = new BufferedReader(new FileReader(inputFile));
```

Using `BufferedReader` to read each line from the input CSV file.

2. **Identify key field**:

```java
int keyFieldIndex = 0;
```

Specifies the index of the key field, that is `ID` column (index 0).

3. **Remove duplicates**

```java
Map<String, String> uniqueEntries = new LinkedHashMap<>();
```

Using `LinkedHashMap` named `uniqueEntries` to store unique data/entries based on the key field.

```java
String key = fields[keyFieldIndex].trim(); // Extract key field value
// Store in map to keep unique entries based on the key field
if (!uniqueEntries.containsKey(key)) {
    uniqueEntries.put(key, line);
}
```

If the uniqueEntries doesn't contains key, then put the new entries to the output to prevent duplicates.

4. **Write unique entries**:

```java
BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
for (String entry : uniqueEntries.values()) {
    writer.write(entry);
    writer.newLine();
}
writer.close();
```

Iterate over `uniqueEntries` and write each entry to the output CSV file using `BufferedWriter`

#
## 5.4 Get a shallow copy of a HashMap instance

[<ins>`Code - 5.4 Shallow copy HashMap`</ins>](code/src/main/java/org/example/assignment5/ShallowCopyHashMap.java)

A shallow copy of a `HashMap` is a new `HashMap` with mappings to the same key and value objects as the original `HashMap`. To get a shallow copy of a `HashMap`, use the `clone()` method. The object referenced by the keys and values are not cloned, only the references are copied.

```java
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
```

The implementation in the code contain:
- A `HashMap` named `originalMap` is created and populated with some key-value pairs.
- A shallow copy of `originalMap` is created using the ``clone()`` method.
- To test,the original `HashMap` is modified by adding a new `key-value pair`.
- Both the original and the shallow copy `HashMap` instances are printed again to show that the shallow copy remains unchanged after the modification to the original `HashMap`.

#
## 5.5 Convert List to Map (ex: employee with employeeID as a key and order asc by key).

[<ins>`Code - 5.5 Convert List to Map`</ins>](code/src/main/java/org/example/assignment5/ConvertListToMap.java)

Covert a `List` of `Employee` objects into a `Map` where the `employeeID` as a key, by using Java streams with `Collectors.toMap`. The implementation code containing:

1. **List creation**: Create a `List<Employee>` containing instances of the `Employee` class with various values

2. **Conversion**: Using Java streams with the explanation:
- Convert `List<Employee>` to `Map<Integer, Employee>` ordered by employeeID in asc way by key
- `.stream()` converts the list into a stream
- `.collect(Collectors.toMap(...))` collects elements of the stream into a Map
- `Employee::getEmployeeID` is used as the key mapper to extract `employeeID` and order it.
- `emp -> emp` is used as the value mapper to map each Employee object directly to itself.

```java
Map<Integer, Employee> employeeMap = employees.stream()
    .collect(Collectors.toMap(Employee::getEmployeeID, emp -> emp));
```


3. **Output**: After conversion, it will print each key-value pair to console. The output in this code is:

```
101 -> Employee{employeeID=101, name='Alice'}
102 -> Employee{employeeID=102, name='Charlie'}
103 -> Employee{employeeID=103, name='Bob'}
104 -> Employee{employeeID=104, name='Eve'}
105 -> Employee{employeeID=105, name='David'}
```

#
## 5.6 Demo CopyOnWriteArrayList when modify item

[<ins>`Code - 5.6 CopyOnWriteArrayList`</ins>](code/src/main/java/org/example/assignment5/CopyOnWriteArrayListLab.java)

The `CopyOnWriteArrayList` is a thread-safe variant of `ArrayList`. Making a fresh copy of the underlying array for every write operation (add, set, remove). The read operations do not require locks and can proceed concurrently with write operations without being affected by them.

**Summary Implementation**:

```java
// Create a CopyOnWriteArrayList and add some elements
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
list.add("A");
list.add("B");
list.add("C");
```

In initial setup, a `CopyOnWriteArrayList` named `list` is created and populated with elements `"A"`, `"B"`, and `"C"`.

```java
// Thread to modify the list
Thread writerThread = new Thread(() -> {
    list.set(1, "X"); // Modify element at index 1
    list.add("D");    // Add a new element
    System.out.println("Writer thread updated list: " + list);
}, "Writer Thread");
```

A `writer thread` is created that modifies the list by changing the element at index `1` to `"x"`, and then adding a new element `"D"`.

```java
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
```

A `reader thread` is created that iterates over the list and prints for each element, the `sleeps` is for simulate of some work.

```java
// Start both threads
writerThread.start();
readerThread.start();
```

Both `threads` are started concurrently.

```java
System.out.println("Final list: " + list); // Output: Final list: [A, X, C, D]
```

The final list is printed.

`Output`:
```
Initial list: [A, B, C]
Final list: [A, X, C, D]
Writer thread updated list: [A, X, C, D]
Reader thread reads: A
Reader thread reads: X
Reader thread reads: C
Reader thread reads: D
```

**Points**:
The `CopyOnWriteArrayList` ensures that the `reader thread` has consistent view of the list even while the `writer thread` is modifying it (Thread Safety). The `reader thread` working with the original list, while the `writer thread` working with a modified copy of the list.

#
## 5.7 Demo ConcurrencyHashMap

[<ins>`Code - 5.7 ConcurrencyHashMap`</ins>](code/src/main/java/org/example/assignment5/ConcurrencyHashMap.java)

The `ConcurrentHashMap` is a thread-safe variant of `HashMap` that is designed for concurrent access. `ConcurrentHashMap` uses a finer-grained locking mechanism to allow higher concurrency for each operation.

**Summary Implementation**:

```java
// Create a ConcurrentHashMap
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("A", 1); // Add some elements
map.put("B", 2);
map.put("C", 3);
map.put("D", 4);
```

Create a `ConcurrentHashMap` named `map` and populate with key-value pairs: ("A", 1), ("B", 2), ("C", 3), and ("D", 4).

```java
// Thread to modify the map
Thread writerThread = new Thread(() -> {
    map.put("B", 35); // Modify an existing element, B -> 35
    map.put("E", 5);  // Add a new element, E -> 5
    System.out.println("Updated map: " + map);
}, "Writer Thread");
```

Create a `writer thread` that modifies the map. Updating the value of `"B"` key to `35`. Add a new key-value `("D", 4)`

```java
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
```

Create a `reader thread` that read the map by iterates over the entries and prints each key-value pair.

```java
// Start both threads
writerThread.start();
readerThread.start();
```
Start both tread concurrently

`Output`:
```
Initial map: {A=1, B=2, C=3, D=4}
Final map: {A=1, B=35, C=3, D=4, E=5}
Updated map: {A=1, B=35, C=3, D=4, E=5}
Map reads: A -> 1
Map reads: B -> 35
Map reads: C -> 3
Map reads: D -> 4
Map reads: E -> 5
```

**Points**:
The `ConcurrentHashMap` allow safe concurrent read and write operations without declaring synchronzation explicitly.

#
## 5.8 Explain equal() and hashCode() method

**Definition**

The `equals()` and `hashCode()` are methods for working with collections like `HashMap`, `HashSet`, and others.

The **`equals()`** method is used to determina if two objects are considered equal. Example:

```java
public class Person {
    private int id;      // Unique identifier
    private String name; // Name

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the compared objects are the same instance

        if (o == null || getClass() != o.getClass()) return false; // Check if the compared object is null or not of the same class
        
        Person person = (Person) o; // Cast the object to Person

        // Check if both id and name are equal for both objects
        return id == person.id && Objects.equals(name, person.name); 
    }
}
```

The **`hashCode()`** method returns an integer hash code value for the object. This method is used in hashing-based collections like `HashMap`, `HashSet`, and `Hashtable`.

```java
public class Person {
    private int id;      // Unique identifier
    private String name; // Name

    @Override
    public boolean equals(Object o) {
        ...
    }

    @Override
    public int hashCode() {
        // Generate a hash code using name and id
        return Objects.hash(name, id);
    }
}
```

The relationship between `equals()` and `hashCode()` are if `equals()` is overridden, then the `hashCode()` must also be overriden to maintain the contract, which states that equal objects must have equal `hash codes`. If two objects are equal according to the `equals()` method, both must have the same hash code. And if two object have the same hash code, it's not necessarily equal.

**Comparation**:

| Criteria | equals() | hashCode() |
| --- | --- | --- |
| **Purpose** | To chech logical equality of two objects, but not their reference equality that is `==` | To provide an integer representation of the object in `hashing` |
| **Default Implementation** | Compare memory address, two references are equal only if they point to the same references | Provide distinct integers for different objects |
| **Override** | To compare the contents of objects | Ensure that equal objects have the same hash code |

#
## 5.9 See assignment 5.5, add employee to HashSet. How can it recognize that 2 employee has duplicated employee ID? Implement it

[<ins>`Code - 5.9 HashSet Employee`</ins>](code/src/main/java/org/example/assignment5/HashSetEmployee.java)

To recognize duplicate employee ID when adding `Employee` objects to a `HashSet`, override the `equals()` and `hashCode()` methods in the model, that is `Employee` class. This ensures that two `Employee` objects with the same `employeeID` are equal.

**Implementation**:

```java
List<Employee> employees = Arrays.asList(
    new Employee(101, "John"),
    new Employee(103, "Michael"),
    new Employee(102, "Charlie"),
    new Employee(105, "David"),
    new Employee(104, "Eve"),
    new Employee(103, "Michael Duplicate") // Duplicate employeeID
);
```
First, add a duplicate employee to the list as a demonstration, ensurint there is no duplicated employee ID.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true; // Check if the compared objects are the same instance
    if (o == null || getClass() != o.getClass()) return false; // Check if the compared object is null or not of the same class
    Employee employee = (Employee) o; // Cast the object to Employee
    return employeeID == employee.employeeID; // Check if the employee IDs are equal
}

@Override
public int hashCode() {
    return Objects.hash(employeeID); // Generate a hash code using employeeID
}
```

Overriding `equals()` and `hashCode()` ensures that two `Employee` objects with the same `employeeID` are equal. The `equals()` method compares the `employeeID` field, and the `hashCode()` method generates a hash code based on `employeeID`.

```java
class Employee {
    ... // Other code

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the compared objects are the same instance
        if (o == null || getClass() != o.getClass()) return false; // Check if the compared object is null or not of the same class
        Employee employee = (Employee) o; // Cast the object to Employee
        return employeeID == employee.employeeID; // Check if the employee IDs are equal
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID); // Generate a hash code using employeeID
    }
}
```

```java
// Add employees to a HashSet to recognize duplicates by employee ID
Set<Employee> employeeSet = new HashSet<>(employees);
```

Add `Employee` objects to a `HashSet`, the set use the overridden `equals()` and `hashCode()` methods to determine if an object is duplicate. If an `Employee` with the same `employeeID` exists, it won't be added again.

**`Output`**:
```
HashSet of Employees:
Employee{employeeID=101, name='John'}
Employee{employeeID=102, name='Charlie'}
Employee{employeeID=103, name='Michael'}
Employee{employeeID=104, name='Eve'}
Employee{employeeID=105, name='David'}
```

There is no duplicate key, that is Employee(103, "Michael Duplicate").

#
## 5.10 Similiar (9), create Map of employee with composite key (department, employeeID)

[<ins>`Code - 5.10 Composite Key Map of Employee`</ins>](code/src/main/java/org/example/assignment5/CompositeKeyMapEmployee.java)

A `Composite Key` is a key that consists of multiple attributes. Used to uniquely identify a record or entry in a collection. In this implementation, for the key is using a composite key object, that is from `MapKey` class.

**`Implementation`**:

```java
class MapKey {
    private String department;
    private int employeeID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if the compared objects are the same instance
        if (o == null || getClass() != o.getClass()) return false; // Check if the compared object is null or not of the same class
        MapKey that = (MapKey) o; // Cast the object to MapKey
        // Check if both department and employeeID are equal
        return employeeID == that.employeeID && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, employeeID); // Generate a hash code using department and employeeID
    }

    ... // Other code
}
```

The `MapKey` class is used to create a composite key containing `department` and `employeeID`. The `equals()` and `hashCode()` methods are overriden for a hash-based collection.

```java
class Employee {
    private int employeeID;
    private String name;
    private String department;

    ...
}
``` 

The `Employee` class has `department` field to match with the composite key.


```java
public static void main(String[] args) {
    List<Employee> employees = Arrays.asList(
        new Employee(101, "John", "Sales"),
        new Employee(103, "Michael", "IT"),
        new Employee(102, "Charlie", "HR"),
        new Employee(105, "David", "IT"),
        new Employee(104, "Eve", "Sales"),
    );

    // Convert List<Employee> to Map<CompositeKey, Employee>
    Map<CompositeKey, Employee> employeeMap = employees.stream()
        .collect(Collectors.toMap(
            emp -> new CompositeKey(emp.getDepartment(), emp.getEmployeeID()), // Composite key mapper
            emp -> emp // Value mapper
        ));

    // Print the resulting map
    employeeMap.forEach((key, value) -> System.out.println(key + " -> " + value));
}
```

Create a list of `Employee` objects that has `department` field. A list of `Employee` converted into a `Map<MapKey, Employee>` using the `MapKey` as the key (composite). Ensure that each `Employee` is uniquely identified by a combination of `department` and `employeeID`.

**`Output`**:
```
MapKey{department='Sales', employeeID=101} -> Employee{employeeID=101, name='John', department='Sales'}
MapKey{department='Sales', employeeID=104} -> Employee{employeeID=104, name='Eve', department='Sales'}
MapKey{department='IT', employeeID=103} -> Employee{employeeID=103, name='Michael', department='IT'}
MapKey{department='HR', employeeID=102} -> Employee{employeeID=102, name='Charlie', department='HR'}
MapKey{department='IT', employeeID=105} -> Employee{employeeID=105, name='David', department='IT'}
```

Each `Employee` identified by a key that is combination of `department` and `employeeID`.

#
## 5.11 What is issue with below code. Explain and fix

**`The code`**:

```java
public static void demo1() {
    List<String> data = new ArrayList<>();
    data.add("Joe");
    data.add("Helen");
    data.add("Test");
    data.add("Rien");
    data.add("Ruby");
    for (String d: data) {
        if (d.equals("Test")) {
            data.remove(d);
        }
    }
}
```

**`Explain The Issue`**:

When using an enhanced for loop (for-each loop) to iterate over a collection (`ArrayList` in this case), an implicit `iterator` is used. The `iterator` maintains a `modification count` as an internal state that incremented every time the collection is modified, like when elements are added or removed.

During the iteration, the `iterator` checks if the `count` changes unexpected. If directly modify the collection by `data.remove(d)`, the `modification count` changes but the `iterator` is not aware of the changes because it did not initiate.

When the `iterator` detects a unexpected modification, it throws a `ConcurrentModificationException` as a signal that collection has been concurrently modified.

**`Fix`**:

To avoid the exception, use an explicit `iterator` to call the `remove` method to remove elements safely, or it can be use `streams` to filter out the elements. In this example, I'll using the `Streams` to fix the code.

```java
public static void demo1() {
    List<String> data = new ArrayList<>();
    data.add("Joe");
    data.add("Helen");
    data.add("Test");
    data.add("Rien");
    data.add("Ruby");
    
    // Using streams to filter out the "Test" element
    data = data.stream()
        .filter(d -> !d.equals("Test"))
        .collect(Collectors.toList());
}
```

It convert the list to a stream, and then using a `filter` method to exclude the "Test" element, lastly it collect the filtered elements back into a list.

#
## 5.12 What happen multiple threads to access and modify a shared collection concurrently? Note: ConcurrencyModificationException

When multiple threads access and modify a shared collection concurrently without proper synchronization, there will be several issues arise:

- `ConcurrentModificationException`

    The `ConcurrentModificationException` is a runtime exception when a collection is modified concurrently while beign iterated. The exception thrown when a single thread modifies the collection directly while another thread is iterating the collection using an iterator or an enhanced for loop.

- `Data Problem`

    When multiple threads modify a collection simultaneously, the internal data structure can become incosistent, even might get corrupted because the unpredicable behavior.

**Prevent The Issues**

To prevent the issues that can be arise because the multiple threads access and modify a shared collection concurrently, it can be done in the following:

- `Synchronization`

    Using synchronized methods to ensure only one thread can modify the collection at a time to prevent concurrent modifications.

    ```java
    synchronized (list) {
        list.remove("C");
    }
    ```

- `Concurrent Collections`

    There is concurrent collection classes in the `java.util.concurrent` package to handle the concurrent access and modification safely, such as `ConcurrentHashMap`, `ConcurrentLinkedQueue`, and `CopyOnWriteArrayList`.