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
- `.stream()` converts the list into a stream
- `.collect(Collectors.toMap(...))` collects elements of the stream into a Map
- `Employee::getEmployeeID` is used as the key mapper to extract `employeeID`.
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