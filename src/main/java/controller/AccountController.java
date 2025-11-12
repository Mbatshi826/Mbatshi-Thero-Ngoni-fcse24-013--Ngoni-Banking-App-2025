package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import model.Account;
import model.Customer;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;;

public class AccountController {
    private final AccountDAO accountDAO = new AccountDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO(); // DAO for transactions

    public Account createAccountForCustomer(long customerId, Account account) throws SQLException {
        Customer cust = customerDAO.findById(customerId);
        if (cust == null) throw new IllegalArgumentException("Customer not found");
        long id = accountDAO.create(account, customerId);
        account.setId(id);
        return account;
    }

    public void deposit(long accountNumber, BigDecimal amount) throws SQLException {
        Account acc = accountDAO.findByAccountNumber(accountNumber);
        if (acc == null) throw new IllegalArgumentException("Account not found");

        acc.deposit(amount);
        accountDAO.updateBalance(acc.getId(), acc.getBalance());

        // Record transaction
        transactionDAO.recordTransaction(acc.getId(), amount, "Deposit");
    }

    public void withdraw(long accountNumber, BigDecimal amount) throws SQLException {
        Account acc = accountDAO.findByAccountNumber(accountNumber);
        if (acc == null) throw new IllegalArgumentException("Account not found");

        boolean success = acc.withdraw(amount);
        if (!success) throw new IllegalArgumentException("Insufficient funds");

        accountDAO.updateBalance(acc.getId(), acc.getBalance());

        // Record transaction
        transactionDAO.recordTransaction(acc.getId(), amount, "Withdrawal");
    }

    public BigDecimal applyInterest(long accountNumber) throws SQLException {
        Account acc = accountDAO.findByAccountNumber(accountNumber);
        if (acc == null) throw new IllegalArgumentException("Account not found");
        if (acc instanceof model.InterestBearing ib) {
            BigDecimal interest = ib.applyMonthlyInterest();
            accountDAO.updateBalance(acc.getId(), acc.getBalance());

            // Record interest transaction
            transactionDAO.recordTransaction(acc.getId(), interest, "Interest");

            return interest;
        } else {
            throw new IllegalArgumentException("Account does not earn interest");
        }
    }

    public List<Account> getAccountsForCustomer(long customerId) throws SQLException {
        return accountDAO.findByCustomerId(customerId);
    }
}
