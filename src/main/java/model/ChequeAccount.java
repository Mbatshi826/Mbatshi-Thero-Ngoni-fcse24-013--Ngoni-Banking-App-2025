package model;

import java.math.BigDecimal;

public class ChequeAccount extends Account {
    private String companyName;
    private BigDecimal overdraftLimit = new BigDecimal("1000.00"); // Default overdraft limit

    public ChequeAccount(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber,
                         String companyName) {
        super(owner, initialDeposit, branch, accountNumber);
        if (companyName == null || companyName.isBlank()) throw new IllegalArgumentException("Employer required");
        this.companyName = companyName;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive");
        if (balance.add(overdraftLimit).compareTo(amount) < 0) {
            return false; // Insufficient funds, even with overdraft
        }
        balance = balance.subtract(amount);
        return true;
    }

    public String getCompanyName() { return companyName; }

    @Override
    public String getBranch() {
        return super.getBranch();
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(BigDecimal overdraftLimit) {
        if (overdraftLimit != null && overdraftLimit.compareTo(BigDecimal.ZERO) >= 0) {
            this.overdraftLimit = overdraftLimit;
        }
    }
}
