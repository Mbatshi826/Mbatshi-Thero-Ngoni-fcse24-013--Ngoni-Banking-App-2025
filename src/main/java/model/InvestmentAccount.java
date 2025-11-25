package model;

import java.math.BigDecimal;

public class InvestmentAccount extends Account implements InterestBearing {
    private static final BigDecimal RATE = new BigDecimal("0.05"); // 5% monthly
    private static final BigDecimal MIN_INITIAL = new BigDecimal("500");

    public InvestmentAccount(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber) {
        super(owner, initialDeposit, branch, accountNumber);
        if (initialDeposit.compareTo(MIN_INITIAL) < 0)
            throw new IllegalArgumentException("Investment requires at least BWP500");
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive");
        if (balance.compareTo(amount) < 0) {
            return false; // Insufficient funds
        }
        balance = balance.subtract(amount);
        return true;
    }

    @Override
    public BigDecimal applyMonthlyInterest() {
        BigDecimal interest = balance.multiply(RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
        balance = balance.add(interest);
        return interest;
    }

    @Override
    public String getBranch() {
        return super.getBranch();
    }
}
