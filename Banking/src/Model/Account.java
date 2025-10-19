package Model;

import java.math.BigDecimal;

public abstract class Account {
    protected long accountNumber;
    protected BigDecimal balance;
    protected String branch;
    protected Customer owner;

    public Account(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber) {
        if (owner == null) throw new IllegalArgumentException("Account must have an owner");
        if (initialDeposit.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Initial deposit cannot be negative");

        this.owner = owner;
        this.balance = initialDeposit;
        this.branch = branch;
        this.accountNumber = accountNumber;
    }

    // Abstract methods for subclasses
    public abstract void withdraw(BigDecimal amount);
    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Deposit must be positive");
        balance = balance.add(amount);
    }

    // Getters
    public BigDecimal getBalance() { return balance; }
    public Customer getOwner() { return owner; }
    public long getAccountNumber() { return accountNumber; }
    public String getBranch() { return branch; }
}
