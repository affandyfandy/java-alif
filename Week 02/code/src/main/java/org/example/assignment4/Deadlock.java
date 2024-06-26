package org.example.assignment4;

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
