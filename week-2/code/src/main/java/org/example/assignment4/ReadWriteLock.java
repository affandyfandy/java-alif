package org.example.assignment4;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.*;

class SharedResource {
    private int value = 0; // Shared resource
    private final ReadWriteLock lock = new ReentrantReadWriteLock(); // ReadWriteLock instance

    // Method to read the value
    public int read() {
        lock.readLock().lock(); // Acquire read lock
        try {
            System.out.println(Thread.currentThread().getName() + " is reading the value: " + value);
            return value;
        } finally {
            lock.readLock().unlock(); // Release read lock
        }
    }

    // Method to write a new value
    public void write(int newValue) {
        lock.writeLock().lock(); // Acquire write lock
        try {
            System.out.println(Thread.currentThread().getName() + " is writing the value: " + newValue);
            value = newValue;
        } finally {
            lock.writeLock().unlock(); // Release write lock
        }
    }
}

class ReadWriteLockExample {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource(); // Shared resource instance

        // Executor service to manage threads
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Creating multiple reader tasks
        for (int i = 0; i < 3; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 5; j++) {
                    resource.read(); // Reading the value
                    try {
                        TimeUnit.MILLISECONDS.sleep(100); // Simulating read time
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        // Creating multiple writer tasks
        for (int i = 0; i < 2; i++) {
            final int newValue = i;
            executor.submit(() -> {
                for (int j = 0; j < 5; j++) {
                    resource.write(newValue + j); // Writing new value
                    try {
                        TimeUnit.MILLISECONDS.sleep(150); // Simulating write time
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        // Shutting down the executor service
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
