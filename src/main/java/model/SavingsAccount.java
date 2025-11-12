package model;

import java.math.BigDecimal;

public class SavingsAccount extends Account implements InterestBearing {
    private static final BigDecimal RATE = new BigDecimal("0.0005"); // 0.05%

    public SavingsAccount(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber) {
        super(owner, initialDeposit, branch, accountNumber);
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        throw new UnsupportedOperationException("Withdrawals not allowed from SavingsAccount");
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBranch'");
    }
}
