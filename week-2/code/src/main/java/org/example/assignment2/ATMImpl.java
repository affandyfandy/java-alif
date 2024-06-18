package org.example.assignment2;

public class ATMImpl implements ATM {
    public int idATM;
    public int accountId;

    public ATMImpl(int idATM, int accountId) {
        this.idATM = idATM;
        this.accountId = accountId;
    }

    public boolean withdraw(int amount, double balance) {
        if (balance >= amount) {
            return true;
        }
        return false;
    }

    public boolean deposit(int amount, double balance) {
        return true;
    }

    public double queryBalance(int accountId) {
        return 0.0;
    }

    public boolean login(String username, String password) {
        return true;
    }

    public boolean logout(String username) {
        return true;
    }
}
