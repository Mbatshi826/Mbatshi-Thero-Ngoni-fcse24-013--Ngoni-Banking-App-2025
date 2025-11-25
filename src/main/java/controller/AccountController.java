package controller;

import dao.AccountDAO;
import dao.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Account;
import model.Customer;

import java.sql.SQLException;
import java.util.List;

public class AccountController {

    @FXML
    private ListView<Account> accountListView;

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField amountField;

    @FXML
    private Label messageLabel;

    @FXML
    private Label accountTypeLabel;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label customerNameLabel;

    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;

    public AccountController() {
        this.accountDAO = new AccountDAO();
        this.customerDAO = new CustomerDAO();
        loadAccounts();
    }

    private void loadAccounts() {
        try {
            List<Account> accounts = accountDAO.findAll();
            ObservableList<Account> observableList = FXCollections.observableArrayList(accounts);
            accountListView.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading accounts.");
        }
    }

    @FXML
    public void initialize() {
        accountListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAccountDetails(newValue));
    }

    private void showAccountDetails(Account account) {
        if (account != null) {
            try {
                Customer customer = customerDAO.findById(account.getCustomerId());
                accountTypeLabel.setText("Account Type: " + account.getType());
                balanceLabel.setText("Balance: " + String.format("%.2f", account.getBalance()));
                customerNameLabel.setText("Customer: " + customer.getFirstName() + " " + customer.getLastName());
            } catch (SQLException e) {
                e.printStackTrace();
                messageLabel.setText("Error loading account details.");
            }
        } else {
            clearLabels();
        }
    }

    private void clearLabels() {
        accountTypeLabel.setText("");
        balanceLabel.setText("");
        customerNameLabel.setText("");
    }

    @FXML
    public void deposit() {
        performTransaction("deposit");
    }

    @FXML
    public void withdraw() {
        performTransaction("withdraw");
    }

    private void performTransaction(String type) {
        try {
            long accountNumber = Long.parseLong(accountNumberField.getText());
            double amount = Double.parseDouble(amountField.getText());

            Account account = accountDAO.findByAccountNumber(accountNumber);
            if (account == null) {
                messageLabel.setText("Account not found.");
                return;
            }

            if (type.equals("deposit")) {
                account.deposit(amount);
            } else if (type.equals("withdraw")) {
                account.withdraw(amount);
            }

            accountDAO.update(account);
            messageLabel.setText("Transaction successful.");
            showAccountDetails(account);

        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid account number or amount.");
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("An error occurred.");
        }
    }

    @FXML
    public void applyInterest() {
        // ... (implementation for applyInterest)
    }
}
