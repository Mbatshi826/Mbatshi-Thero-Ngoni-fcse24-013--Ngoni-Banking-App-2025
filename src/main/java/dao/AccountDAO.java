package dao;

import model.Account;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public void add(Account account) throws SQLException {
        String sql = "INSERT INTO Account (accountNumber, customerId, type, balance, branch, companyName) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, account.getAccountNumber());
            pstmt.setLong(2, account.getCustomerId());
            pstmt.setString(3, account.getType());
            pstmt.setDouble(4, account.getBalance());
            pstmt.setString(5, account.getBranch());
            pstmt.setString(6, account.getCompanyName());
            pstmt.executeUpdate();
        }
    }

    public Account findById(long id) throws SQLException {
        String sql = "SELECT * FROM Account WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getLong("id"),
                            rs.getLong("accountNumber"),
                            rs.getLong("customerId"),
                            rs.getString("type"),
                            rs.getDouble("balance"),
                            rs.getString("branch"),
                            rs.getString("companyName")
                    );
                }
            }
        }
        return null;
    }

    public Account findByAccountNumber(long accountNumber) throws SQLException {
        String sql = "SELECT * FROM Account WHERE accountNumber = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getLong("id"),
                            rs.getLong("accountNumber"),
                            rs.getLong("customerId"),
                            rs.getString("type"),
                            rs.getDouble("balance"),
                            rs.getString("branch"),
                            rs.getString("companyName")
                    );
                }
            }
        }
        return null;
    }

    public List<Account> findAll() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                accounts.add(new Account(
                        rs.getLong("id"),
                        rs.getLong("accountNumber"),
                        rs.getLong("customerId"),
                        rs.getString("type"),
                        rs.getDouble("balance"),
                        rs.getString("branch"),
                        rs.getString("companyName")
                ));
            }
        }
        return accounts;
    }

    public void update(Account account) throws SQLException {
        String sql = "UPDATE Account SET balance = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, account.getBalance());
            pstmt.setLong(2, account.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM Account WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}
