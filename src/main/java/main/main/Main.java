package main.main;

import org.h2.tools.Server;
import controller.AccountController;
import controller.LoginController;
import util.DBConnection;
import model.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        // Start H2 Web Console
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        System.out.println("H2 Web Console started at http://localhost:8082");

        System.out.println("Starting Banking System (H2 Test) ...");

        // Initialize DB and create tables if not exist
        try (Connection c = DBConnection.getConnection(); Statement stmt = c.createStatement()) {
            System.out.println("DB OK: " + c);

            // Create tables if not exists
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS customer (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    first_name VARCHAR(50),
                    last_name VARCHAR(50),
                    address VARCHAR(100)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS account (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    customer_id BIGINT,
                    type VARCHAR(20),
                    balance DECIMAL(15,2),
                    account_number BIGINT,
                    FOREIGN KEY (customer_id) REFERENCES customer(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transaction (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    account_id BIGINT,
                    amount DECIMAL(15,2),
                    type VARCHAR(20),
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (account_id) REFERENCES account(id)
                );
            """);
        } catch (SQLException e) {
            System.err.println("Error initializing DB: " + e.getMessage());
        }

        // Your original banking system test
        LoginController login = new LoginController();
        System.out.println("Login admin/1234 -> " + login.authenticate("admin", "1234"));

        var cust = login.createCustomer("Onneile", "Ngoni", "Gaborone");
        System.out.println("Created customer id=" + cust.getId());

        AccountController ac = new AccountController();
        Account inv = new InvestmentAccount(cust, new BigDecimal("1000.00"), "Main", 10001);
        ac.createAccountForCustomer(cust.getId(), inv);
        System.out.println("Created account id=" + inv.getId() + " balance=" + inv.getBalance());

        ac.deposit(inv.getAccountNumber(), new BigDecimal("500.00"));
        System.out.println("After deposit balance=" + ac.getAccountsForCustomer(cust.getId()).get(0).getBalance());

        var interest = ac.applyInterest(inv.getAccountNumber());
        System.out.println("Applied interest=" + interest);
    }
}
