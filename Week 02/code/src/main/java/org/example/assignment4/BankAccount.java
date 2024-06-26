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