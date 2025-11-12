package model;

import java.math.BigDecimal;

public class ChequeAccount extends Account {
    private String companyName;
    public ChequeAccount(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber,
                         String companyName, String companyAddress) {
        super(owner, initialDeposit, branch, accountNumber);
        if (companyName == null || companyName.isBlank()) throw new IllegalArgumentException("Employer required");
        this.companyName = companyName;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("withdraw>0");
        if (balance.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds");
        balance = balance.subtract(amount);
        return false;
    }

    public String getCompanyName() { return companyName; }

    @Override
    public String getBranch() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBranch'");
    }
}
