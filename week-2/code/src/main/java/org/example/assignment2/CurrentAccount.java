package org.example.assignment2;

public class CurrentAccount implements Account {
    private int accountId;
    private double balance;
    private double overdraftLimit;

    public CurrentAccount(int accountId, double overdraftLimit) {
        this.accountId = accountId;
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
        } else {
            System.out.println("Overdraft limit exceeded");
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }

    public int getAccountId() {
        return accountId;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
