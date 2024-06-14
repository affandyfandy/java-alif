# Singleton

Singleton is a design pattern that ensures a class has only one instance and provides a global point of access to that instance.

# Thead-safe singleton

Thread safety refers to ensures it behaves correctly during simultaneous execution by multiple threads. In Java, when multiple threads access a shared resource concurrently, without proper synchronization or control mechanisms, it can lead to race conditions, where the outcome depends on the timing of execution of threads. Race conditions is when two or more threads can access shared data and they try to change it at the same time.

```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance.");
        }
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

The volatile keyword ensures that multiple threads handle the instance variable when it is being initialized to the Singleton instance. It ensures that the writes to the variable are visible to all threads.

The constructor is private to prevent any other class from instantiating it. This ensures that the Singleton class cannot be instantiated from outside the class.

The getInstance() method reduces the overhead of obtaining a lock by first checking whether the instance is null. Only if the instance is null, then this method will synchronize. Inside the synchronized block, it checks again whether the instance is null before creating the instance. This ensures that only one instance of the Singleton is created.

#

Structure the main method example

```java
public static void main(String[] args) {
    // Get instance of the Singleton
    Singleton instance1 = Singleton.getInstance();

    // Get another instance of the Singleton
    Singleton instance2 = Singleton.getInstance();

    // Check if both instances are the same
    System.out.println("Are instances the same? " + (instance1 == instance2));
}
```

The instance1 retrieves the Singleton instance using the getInstance() method. It's the first time getInstance() is called, it initializes the Singleton instance.
The instance2 retrieves the Singleton instance again. Since the Singleton pattern ensures there's only one instance, instance2 should refer to the same object as instance1.
