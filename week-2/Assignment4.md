# Assignment 4

## Q: Explain about Deadlock and give example? How to prevent

Deadlock is a situation in concurrent programming where two or more threads are waiting each other to release a resource, makes the treads are blocked forever. Also when a thread is waiting for an object lock that is acquired by another thread, and the other thread is waiting for an object lock that is acquired by the first thread. When the threads are waiting for each other to release the lock or resource forever, this condition is called deadlock.

\
Deadlock example:
```java
public class Deadlock {
    public static void main(String[] args) {
        final String resource1 = "Resource 1";
        final String resource2 = "Resource 2";

        Thread thread1 = new Thread(() -> {
            synchronized (resource1) { // Acquire resource1
                System.out.println("Thread 1: Locked resource 1");

                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) { // Attempt to acquire lock resource2
                    System.out.println("Thread 1: Locked resource 2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) { // Acquire resource2
                System.out.println("Thread 2: Locked resource 2");

                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource1) {  // Attempt to acquire lock resource1
                    System.out.println("Thread 2: Locked resource 1");
                }
            }
        });

        thread1.start(); // Start thread1
        thread2.start(); // Start thread2

        /**
         * Output:
         * Thread 1: Locked resource 1
         * Thread 2: Locked resource 2
         */
    }
}
```

In this `Deadlock` class, there are two threads are created to acquire locks on two resources in different orders. If both threads start at the same time, they may acquire one resource and then wait for other resource to become available. This results in a deadlock where neither thread can proceed.

Preventing deadlock in multi-threaded programming by:
- **Avoid cyclic dependency**: Avoid the need for acquiring multiple locks for a thread, if a thread need multiple locks, ensure that each thread acquires the locks in the same order.
- **Avoid nested locks**: Avoid giving locks to multiple threads.
- **Avoid unnecessary locks**: The locks should be given to the important threads.
- **Using thread join**: Join with a maximum time that a thread will take.

\
Example preventing deadlock:
```java
package org.example.assignment4;

public class DeadlockPrevent {
    final static String resource1 = "Resource 1";
    final static String resource2 = "Resource 2";

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) { // Acquire resource1 first
                System.out.println("Thread 1: Locked resource1");

                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) { // Then acquire resource2
                    System.out.println("Thread 1: Locked resource2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource1) { // Acquire resource1 first
                System.out.println("Thread 2: Locked resource1");

                try {
                    Thread.sleep(100); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) { // Then acquire resource2
                    System.out.println("Thread 2: Locked resource2");
                }
            }
        });

        thread1.start();
        thread2.start();

        /**
         * Output:
         * Thread 1: Locked resource1
         * Thread 1: Locked resource2
         * Thread 2: Locked resource1
         * Thread 2: Locked resource2
         */
    }
}

```

In the `DeadlockPrevent` class, there are two threads (`thread1` and `thread2`) to synchronization using the resources `resource1` and `resource2`. Each thread attempts to acquire locks on these resources in the same order, first `resource1` and then `resource2`. This approach prevents deadlock by ensuring that both threads follow a consistent lock acquisition sequence. If `thread1` acquires `resource1` first, it will wait for `resource2` only after it has released `resource1`. Similarly, `thread2` will acquire `resource1` first and then `resource2`, avoiding a deadlock scenario where one thread holds a lock and waits indefinitely for a resource held by another thread.

#
## Creates a bank account with concurrent deposits and withdrawals using threads.

[Code - Bank Account](code/src/main/java/org/example/assignment4/BankAccount.java)

Implementation:
```java
public class BankAccount{
    private double balance;

    // Synchronized methods to deposit money into the account
    public synchronized void deposit(double amount){
        balance += amount;
    }

    // Synchronized methods to withdraw money from the account
    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance");
        }
    }

    public double getBalance(){
        return balance;
    }
}
```

BankAccount class has two synchronized methods: `deposit` and `withdraw`. The `deposit` method increases the account balance, while the `withdraw` method decreases the account balance.

`Synchronization` ensures that only one thread can execute this method at a time, preventing `concurrent` modification issues.

```java
class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        // Thread for depositing money
        Thread depositThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
                System.out.println("Deposited 100, balance: " + account.getBalance());
            }
        });

        // Thread for withdrawals
        Thread withdrawThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(100);
                System.out.println("Withdrawn 100, balance: " + account.getBalance());
            }
        });

        depositThread.start(); // Start deposit thread
        withdrawThread.start(); // Start withdraw thread
    }
}
```

In the Main class, we create an instance of the `BankAccount` class and two threads, that is `depositThread` and `withdrawThread`. The `depositThread` calls the deposit method to deposit 100 units of money, while the `withdrawThread` calls the withdraw method to withdraw 100 units of money.

\
Output:
```
Withdrawn 100, balance: 0.0
Deposited 100, balance: 100.0
Insufficient balance
Withdrawn 100, balance: 0.0
Deposited 100, balance: 100.0
...
```

When we run the program, the deposit and withdraw operations are performed `concurrently` by the two threads. Since the deposit and withdraw methods are `synchronized`, only one thread can execute these methods at a time, preventing multiple threads from accessing the account balance simultaneously and ensuring that the account balance is updated correctly.

#
## Write a Java program that sorts an array of integers using multiple threads.

[Code - Multiple Threads Sort](code/src/main/java/org/example/assignment4/MultiThreadSort.java)

The MultiThreadSort class is class for sorts an array of integers using merge sort algorithm.

Run method:
```java
@Override
public void run() {
    ...
}
```

The `run` method is overridden from the `Runnable interface`. If the portion of the array to be sorted has more than one element, it divides the array into two halves, creates two MultiThreadSort objects for each half, and starts two new threads to sort each half. Then waits for both threads to finish using the join method, and merges the sorted halves using the merge method.

\
Merge method:
```java
private void merge(int start, int mid, int end) {
    ...
}
```
The `merge` method merges two sorted halves of an array.  It iterates over the two halves and inserts the smaller element into the temporary array, the temporary array is used for store the merged result. If there are remaining elements in either half after the iteration, then inserts into the temporary array. Finally, it copies the sorted elements from the temporary array back to the original array.

\
Main
```java
public static void main(String[] args) {
    ...
}
```

The main method creates an array of integers and a `MultiThreadSort` object to sort the array. It then creates a thread with the `MultiThreadSort` object and starts the thread. After the sorting is done, it prints the sorted array.

