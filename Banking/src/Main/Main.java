package Main;

import Dao.Database;
import Model.*;

import java.math.BigDecimal;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Welcome to the Ngoni Banking App");

        // Test database connection first
        try (Connection conn = Database.getConnection()) {
            System.out.println("Connected successfully to MySQL via XAMPP!");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return; // stop program if DB not connected
        }

        //  Create a new customer
        Customer onneile = new Customer("Onneile", "Ngoni", "Gaborone");
        System.out.println("\n New Customer Created: " + onneile);

        // Create different accounts for the customer
        SavingsAccount savings = new SavingsAccount(onneile, new BigDecimal("2000.00"), "Main Branch", 1001);
        InvestmentAccount investment = new InvestmentAccount(onneile, new BigDecimal("5000.00"), "Main Branch", 2001);
        ChequeAccount cheque = new ChequeAccount(onneile, new BigDecimal("3000.00"), "Main Branch", 3001,
                "BAC University", "Gaborone, Botswana");

        //  Display account details
        System.out.println("\n Account Summary:");
        System.out.println("Savings Account Balance: BWP " + savings.getBalance());
        System.out.println("Investment Account Balance: BWP " + investment.getBalance());
        System.out.println("Cheque Account Balance: BWP " + cheque.getBalance());

        //  Perform some operations
        savings.deposit(new BigDecimal("9000.00"));
        investment.withdraw(new BigDecimal("7000.00"));
        cheque.deposit(new BigDecimal("9000.00"));

        System.out.println("\n After Transactions:");
        System.out.println("Savings Account Balance: BWP " + savings.getBalance());
        System.out.println("Investment Account Balance: BWP " + investment.getBalance());
        System.out.println("Cheque Account Balance: BWP " + cheque.getBalance());

        //  Apply interest to applicable accounts
        BigDecimal savingsInterest = savings.applyMonthlyInterest();
        BigDecimal investmentInterest = investment.applyMonthlyInterest();

        System.out.println("\n After Applying Monthly Interest:");
        System.out.println("Savings Interest: BWP " + savingsInterest);
        System.out.println("Investment Interest: BWP " + investmentInterest);
        System.out.println("Savings New Balance: BWP " + savings.getBalance());
        System.out.println("Investment New Balance: BWP " + investment.getBalance());

        //  Display final summary
        System.out.println("\n Banking System Simulation Complete!");
    }
}
