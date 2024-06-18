package org.example.assignment2;

public interface ATM {
    boolean withdraw(int amount, double balance);
    boolean deposit(int amount, double balance);
    double queryBalance(int accountId);
    boolean login(String username, String password);
    boolean logout(String username);
}
