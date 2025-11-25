package main.main;

import org.mindrot.jbcrypt.BCrypt;
import util.DBConnection;
import view.App;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        initDB();
        addTestUser();
        App.main(args);
    }

    private static void addTestUser() {
        String username = "testuser";
        String password = "password123";
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        String userType = "customer";

        String sql = "INSERT INTO user (username, password_hash, user_type) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE password_hash = ?;";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, userType);
            ps.setString(4, passwordHash);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initDB() {
        String customer = "CREATE TABLE IF NOT EXISTS customer ( " +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "first_name VARCHAR(255) NOT NULL, " +
                "last_name VARCHAR(255) NOT NULL, " +
                "address VARCHAR(255) NOT NULL);";
        String account = "CREATE TABLE IF NOT EXISTS account ( " +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "account_number BIGINT NOT NULL UNIQUE, " +
                "customer_id INT NOT NULL, " +
                "type VARCHAR(255) NOT NULL, " +
                "balance DECIMAL(15, 2) NOT NULL, " +
                "branch VARCHAR(255) NOT NULL, " +
                "company_name VARCHAR(255), " + // Added for ChequeAccount
                "FOREIGN KEY (customer_id) REFERENCES customer(id));";
        String transaction = "CREATE TABLE IF NOT EXISTS transaction ( " +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "account_id INT NOT NULL, " +
                "amount DECIMAL(15, 2) NOT NULL, " +
                "type VARCHAR(255) NOT NULL, " +
                "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (account_id) REFERENCES account(id));";
        String user = "CREATE TABLE IF NOT EXISTS user ( " +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL UNIQUE, " +
                "password_hash VARCHAR(255) NOT NULL, " +
                "user_type VARCHAR(255) NOT NULL);";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(customer);
            st.execute(account);
            st.execute(transaction);
            st.execute(user);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
