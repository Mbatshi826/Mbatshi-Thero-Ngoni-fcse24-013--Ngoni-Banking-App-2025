package Model;

import java.math.BigDecimal;

public class InvestmentAccount extends Account implements InterestBearing {
    private static final BigDecimal RATE = new BigDecimal("0.05"); // 5%

    public InvestmentAccount(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber) {
        super(owner, initialDeposit, branch, accountNumber);
        if (initialDeposit.compareTo(new BigDecimal("500")) < 0) {
            throw new IllegalArgumentException("Investment account requires at least BWP500 initial deposit");
        }
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < -1) throw new IllegalArgumentException("Insufficient funds");
        balance = balance.subtract(amount);
    }

    @Override
    public BigDecimal applyMonthlyInterest() {
        BigDecimal interest = balance.multiply(RATE);
        balance = balance.add(interest);
        return interest;
    }
}
