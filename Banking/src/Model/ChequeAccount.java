package Model;

import java.math.BigDecimal;

public class ChequeAccount extends Account {
    private String companyName;
    private String companyAddress;

    public ChequeAccount(Customer owner, BigDecimal initialDeposit, String branch, long accountNumber,
                         String companyName, String companyAddress) {
        super(owner, initialDeposit, branch, accountNumber);
        if (companyName == null || companyName.isEmpty())
            throw new IllegalArgumentException("Company name required for Cheque Account");
        this.companyName = companyName;
        this.companyAddress = companyAddress;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds");
        balance = balance.subtract(amount);
    }

    // Getters
    public String getCompanyName() { return companyName; }
    public String getCompanyAddress() { return companyAddress; }
}
