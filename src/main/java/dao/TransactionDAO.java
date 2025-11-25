package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DBConnection;

public class TransactionDAO {
    public void recordTransaction(long accountId, BigDecimal amount, String type) throws SQLException {
        String sql = "INSERT INTO transaction (accountId, amount, type, timestamp) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            stmt.setBigDecimal(2, amount);
            stmt.setString(3, type);
            stmt.executeUpdate();
        }
    }
}
