package model;

import java.math.BigDecimal;

public abstract class Account {
    protected Long id; // DB id
    protected long accountNumber;
    protected BigDecimal balance;
    protected String branch;
    protected Customer owner;

    public Account(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber) {
        if (owner == null) throw new IllegalArgumentException("owner required");
        this.owner = owner;
        this.balance = initialDeposit == null ? BigDecimal.ZERO : initialDeposit;
        this.branch = branch;
        this.accountNumber = accountNumber;
    }

    public long getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }
    public Customer getOwner() { return owner; }
    public void deposit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("deposit>0");
        balance = balance.add(amount);
    }
    public abstract boolean withdraw(BigDecimal amount);
    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }

    public abstract String getBranch();
}
