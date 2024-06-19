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
