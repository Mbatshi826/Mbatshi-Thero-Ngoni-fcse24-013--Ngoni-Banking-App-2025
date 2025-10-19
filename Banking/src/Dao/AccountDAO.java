package Dao;

import Model.Account;
import Model.Customer;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private final Connection connection;
    private final CustomerDAO customerDAO;

    public AccountDAO(Connection connection) {
        this.connection = connection;
        this.customerDAO = new CustomerDAO(connection);
    }

    // Save a new account
    public void addAccount(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, balance, branch, owner_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, account.getAccountNumber());
            stmt.setBigDecimal(2, account.getBalance());
            stmt.setString(3, account.getBranch());
            stmt.setLong(4, account.getOwner().getId());
            stmt.executeUpdate();
        }
    }

    // Get account by account number
    public Account getAccountByNumber(long accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, accountNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    long ownerId = rs.getLong("owner_id");
                    Customer customer = customerDAO.getCustomerById(ownerId);
                    if (customer == null) throw new SQLException("Customer not found for account");

                    // Use an anonymous subclass or concrete subclass if available
                    return new Account(customer,
                            rs.getBigDecimal("balance"),
                            rs.getString("branch"),
                            rs.getLong("account_number")) {
                        @Override
                        public void withdraw(BigDecimal amount) {
                            // Dummy implementation
                            if (amount.compareTo(balance) > 0)
                                throw new IllegalArgumentException("Insufficient funds");
                            balance = balance.subtract(amount);
                        }
                    };
                }
            }
        }
        return null;
    }

    // Update account balance and branch
    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE accounts SET balance = ?, branch = ?, owner_id = ? WHERE account_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, account.getBalance());
            stmt.setString(2, account.getBranch());
            stmt.setLong(3, account.getOwner().getId());
            stmt.setLong(4, account.getAccountNumber());
            stmt.executeUpdate();
        }
    }

    // Delete account
    public void deleteAccount(long accountNumber) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, accountNumber);
            stmt.executeUpdate();
        }
    }

    // Get all accounts
    public List<Account> getAllAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long ownerId = rs.getLong("owner_id");
                Customer customer = customerDAO.getCustomerById(ownerId);
                if (customer == null) continue;

                Account account = new Account(customer,
                        rs.getBigDecimal("balance"),
                        rs.getString("branch"),
                        rs.getLong("account_number")) {
                    @Override
                    public void withdraw(BigDecimal amount) {
                        if (amount.compareTo(balance) > 0)
                            throw new IllegalArgumentException("Insufficient funds");
                        balance = balance.subtract(amount);
                    }
                };
                accounts.add(account);
            }
        }
        return accounts;
    }
}
