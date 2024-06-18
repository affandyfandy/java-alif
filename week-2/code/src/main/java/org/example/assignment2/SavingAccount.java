package org.example.assignment2;

public class SavingAccount implements Account {
    private int accountId;
    private double balance;

    public SavingAccount(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance");
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getAccountType() {
        return "Saving Account";
    }

    public int getAccountId() {
        return accountId;
    }

    public void addInterest(double rate) {
        balance += balance * rate / 100;
    }
}
