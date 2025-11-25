package model;

import java.math.BigDecimal;

public class SavingsAccount extends Account implements InterestBearing {
    private static final BigDecimal RATE = new BigDecimal("0.0005"); // 0.05%

    public SavingsAccount(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber) {
        super(owner, initialDeposit, branch, accountNumber);
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            return false; // Insufficient funds
        }
        balance = balance.subtract(amount);
        return true;
    }

    @Override
    public BigDecimal applyMonthlyInterest() {
        BigDecimal interest = balance.multiply(RATE);
        interest = interest.setScale(2, BigDecimal.ROUND_HALF_UP);
        balance = balance.add(interest);
        return interest;
    }

    @Override
    public String getBranch() {
        return super.getBranch();
    }
}
