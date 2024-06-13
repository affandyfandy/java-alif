package org.example.assignment.src;

public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
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

    public static void main(String[] args) {
        // Get instance of the Singleton
        Singleton instance1 = Singleton.getInstance();

        // Get another instance of the Singleton
        Singleton instance2 = Singleton.getInstance();

        // Check if both instances are the same
        System.out.println("Are instances the same? " + (instance1 == instance2));
    }
}
