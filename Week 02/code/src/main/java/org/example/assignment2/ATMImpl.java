package org.example.assignment2;

public class ATMImpl implements ATM {
    private int idATM;
    private int accountId;

    private SavingAccount savingAccount;
    private CurrentAccount currentAccount;

    public ATMImpl(int idATM) {
        this.idATM = idATM;
        this.savingAccount = new SavingAccount(1);
        this.currentAccount = new CurrentAccount(2, 1000);
    }

    @Override
    public boolean withdraw(int accountId, double amount) {
        Account account = getAccount(accountId);
        if (account != null) {
            account.withdraw(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(int accountId, double amount) {
        Account account = getAccount(accountId);
        if (account != null) {
            account.deposit(amount);
            return true;
        }
        return false;
    }

    @Override
    public double queryBalance(int accountId) {
        Account account = getAccount(accountId);
        if (account != null) {
            return account.getBalance();
        }
        return -1;
    }

    @Override
    public boolean login(String cardNumber, String pin) {
        if ("1234".equals(cardNumber) && "5678".equals(pin)) {
            this.accountId = 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean logout(String cardNumber) {
        this.accountId = -1;
        return true;
    }

    private Account getAccount(int accountId) {
        if (accountId == 1) {
            return savingAccount;
        } else if (accountId == 2) {
            return currentAccount;
        }
        return null;
    }

    public static void main(String[] args) {
        ATM atm = new ATMImpl(101);

        // Login
        if (atm.login("1234", "5678")) {
            System.out.println("Login successful!");

            atm.deposit(1, 1000);
            System.out.println("Balance after deposit: " + atm.queryBalance(1));

            atm.withdraw(1, 200);
            System.out.println("Balance after withdrawal: " + atm.queryBalance(1));

            if (atm.logout("1234")) {
                System.out.println("Logout successful!");
            }
        } else {
            System.out.println("Login failed!");
        }
    }
}
