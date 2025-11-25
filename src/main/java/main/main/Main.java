package main.main;

import util.DBConnection;
import view.App;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        initDB();
        App.main(args);
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

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            st.execute(customer);
            st.execute(account);
            st.execute(transaction);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
