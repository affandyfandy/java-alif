package org.example.assignment2;

public interface Account {
    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();

    default String getAccountType() {
        return "Generic Account";
    }

    static void printAccountType(Account account) {
        System.out.println("Account Type: " + account.getAccountType());
    }
}

