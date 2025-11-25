package dao;

import model.*;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public long create(Account account, long customerId) throws SQLException {
        String sql = "INSERT INTO account(account_number, customer_id, type, balance, branch, company_name) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, account.getAccountNumber());
            ps.setLong(2, customerId);
            ps.setString(3, account.getClass().getSimpleName().toUpperCase().replace("ACCOUNT", ""));
            ps.setBigDecimal(4, account.getBalance());
            ps.setString(5, account.getBranch());
            if (account instanceof ChequeAccount) {
                ps.setString(6, ((ChequeAccount) account).getCompanyName());
            } else {
                ps.setNull(6, Types.VARCHAR);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    account.setId(id);
                    return id;
                }
            }
            return -1;
        }
    }

    public Account findByAccountNumber(long accountNumber) throws SQLException {
        String sql = "SELECT * FROM account WHERE account_number=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return mapRowToAccount(rs);
            }
        }
        return null;
    }

    public List<Account> findByCustomerId(long customerId) throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE customer_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRowToAccount(rs));
            }
        }
        return list;
    }

    public boolean updateBalance(long accountId, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE account SET balance=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, newBalance);
            ps.setLong(2, accountId);
            return ps.executeUpdate() > 0;
        }
    }

    private Account mapRowToAccount(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        long accNum = rs.getLong("account_number");
        BigDecimal bal = rs.getBigDecimal("balance");
        String branch = rs.getString("branch");
        long id = rs.getLong("id");
        long customerId = rs.getLong("customer_id");
        String companyName = rs.getString("company_name");

        // create a minimal Customer holder for owner (only id)
        Customer owner = new Customer("","",null);
        owner.setId(customerId);

        Account acc;
        if ("SAVINGS".equalsIgnoreCase(type)) acc = new SavingsAccount(owner, bal, branch, accNum);
        else if ("INVESTMENT".equalsIgnoreCase(type)) acc = new InvestmentAccount(owner, bal, branch, accNum);
        else acc = new ChequeAccount(owner, bal, branch, accNum, companyName);

        acc.setId(id);
        return acc;
    }
}
