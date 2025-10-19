package Model;

import java.math.BigDecimal;

public class SavingsAccount extends Account implements InterestBearing {
    private static final BigDecimal RATE = new BigDecimal("0.0005"); // 0.05%

    public SavingsAccount(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber) {
        super(owner, initialDeposit, branch, accountNumber);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        throw new UnsupportedOperationException("Withdrawals not allowed from Savings Account");
    }

    @Override
    public BigDecimal applyMonthlyInterest() {
        BigDecimal interest = balance.multiply(RATE);
        balance = balance.add(interest);
        return interest;
    }
}
