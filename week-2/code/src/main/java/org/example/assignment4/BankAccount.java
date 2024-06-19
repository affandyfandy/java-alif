package org.example.assignment4;

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

// Explanation
// In this program, we have a BankAccount class that represents a bank account. The class has two synchronized methods: deposit and withdraw. The deposit method increases the account balance by the specified amount, while the withdraw method decreases the account balance by the specified amount if the balance is sufficient. If the balance is insufficient, a message is printed indicating that there is insufficient balance.

// In the Main class, we create an instance of the BankAccount class and two threads: depositThread and withdrawThread. The depositThread calls the deposit method to deposit 100 units of money into the account, while the withdrawThread calls the withdraw method to withdraw 100 units of money from the account.

// When we run the program, the deposit and withdraw operations are performed concurrently by the two threads. Since the deposit and withdraw methods are synchronized, only one thread can execute these methods at a time, preventing

// multiple threads from accessing the account balance simultaneously and ensuring that the account balance is updated correctly.
