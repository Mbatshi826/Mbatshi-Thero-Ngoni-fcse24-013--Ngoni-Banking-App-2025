package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/mbatshibankingdb";
    private static final String USER = "root";
    private static final String PASS = ""; // Default for XAMPP

    public static Connection getConnection() throws SQLException {
        try {
            // Ensure driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL driver not found", e);
        }

        // Return active connection
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
