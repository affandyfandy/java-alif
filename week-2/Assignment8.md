# Assignment 8

[<ins>`Assignment 8 Code`</ins>](code/src/main/java/org/example/assignment8)

## 8.1 What Is the serialVersionUID?

When a class implements the `Serializable` interface, it allows the instances of that class to be converted into a stream of bytes or serialization. During serialization, the `Java Virtual Machine` ensuring that's the correct class is used to deserialization by uniquely identify the class version. After that, that stream of bytes will reconstructed back into an object or deserialization.

**Points**:

1. **Unique Identifier**
    In `Serializable` class, there is `serialVersionUID` field which is a `static final long` type variable. It serves as version control mechanism to ensure `serialized` and `deserialized` objects are compatible in terms of `serialization` and `deserialization` process.

2. **Automatic Serialization Versioning**
    When an object is `serialized`, the `serialization` process includes a version of the class that identified by `serialVersionUID`. And when the object is `deserialized`, Java compares this version with the current version of the class in the JVM. If these versions are doen't match, `deserialization` may fail with an `InvalidClassException`.

3. **Explicit Declaration**
    Maintaining compatibility across different versions of class by manually declare the `serialVersionUID`, Java will uses this value during serialization and deserialization.

4. **Manual Control**
    Java will automatically generate if a `serialVersionUID` not explicitly declared based on various aspects, this generated `serialVersionUID` can different between JVM implementations.

5. **Versioning Considerations**
    Manage the `serialVersionUID` to ensure backward and forward compatibility when changing a `Serializable` class.

**Example**:

```java
import java.io.Serializable;

public class MyClass implements Serializable {
    private static final long serialVersionUID = 1L;
    
    ...
}
```

The `serialVersionUID` is explicitly declared as `1L`. Increment the `serialVersionUID` to `2L` if the class is changed or modified. Indicating the serialized form has changed.

#
## 8.2 Illustrate Serialization and Deserialization in write list of object (employee) to file and read file to convert to object and using serialVersionUID

`Serialization` is the process of converting an object into a byte of stream, so that it can be easily saved to a file or sent over a network. `Deserialization` is the byte stream is converted back intoa copy of the object. The `serialVersionUID` is a unique identifier for each class during serialization and deserialization progress.

**Illustration**:

Employee Class

```java
class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private String department;

    public Employee(String name, int age, String department) {
        this.name = name;
        this.age = age;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{name='" + name + "', age=" + age + ", department='" + department + "'}";
    }
}
```

The `Employee` class implements `Serializable` and has a `serialVersionUID`. The `serialVersionUID` is used to ensure that during deserialization, the same class was used during serialization before is loaded.

\
Serialization

```java
public class SerializationDeserializationFile {
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("John Doe", 30, "IT"),
            new Employee("Jane Doe", 25, "HR"),
            new Employee("Tom Smith", 35, "Finance"),
            new Employee("Jerry Seinfeld", 40, "Marketing")
        );

        // Serialization of employees
        String fileName = "employees.ser";

        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {  // ObjectOutputStream is used to serialize an object

            out.writeObject(employees); // writeObject() method serializes the object and writes it to the file
            
            System.out.println("Serialized data is saved in employees.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

        ... // Other code
    }
}
```

Create a list of `Employee` objects with its content and a filename to serialized the list. The `FileOutputStream` opens a file output stream to write at specified file. Then, the `ObjectOutputStream` wraps the `FileOutputStream` to serialize objects. The `writeObject` will serializes the object that is list of employees to the file.

\
Deserialization

```java
public class SerializationDeserializationFile {
    public static void main(String[] args) {
        ... // Other code

        String fileName = "employees.ser"; 

        // Deserialization of employees.ser
        List<Employee> employeesDes = null;

        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) { // ObjectInputStream is used to deserialize an object
            employeesDes = (List<Employee>) in.readObject(); // readObject() method deserializes the object from the file
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }

        if (employeesDes != null) {
            System.out.println("\n Deserialized data from employees.ser");
            for (Employee emp : employeesDes) {
                System.out.println(emp);
            }
        } else {
            System.out.println("No employees found");
        }
    }
}
```

The `Deserialization` reads the list of `Employee` objects from the `employees.ser` file. The `FileInputStream` will open a file  input stream to read from the specified file. `ObjectInputStream` profide deserialize object of that file. The `readObject` deserializes the list of employees from the file.

**Output**:
```
Serialized data is saved in employees.ser

Deserialized data from employees.ser
Employee{name='John Doe', age=30, department='IT'}
Employee{name='Jane Doe', age=25, department='HR'}
Employee{name='Tom Smith', age=35, department='Finance'}
Employee{name='Jerry Seinfeld', age=40, department='Marketing'}
```

The program illustrated the serialization  and deserialization successfully.