package view;

import controller.AccountController;
import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;

import java.math.BigDecimal;
import java.util.List;

public class AccountView {
    private final BorderPane view;
    private final AccountController accountController = new AccountController();
    private Customer currentCustomer;

    public AccountView(javafx.stage.Stage stage, String username) {
        view = new BorderPane();

        Label header = new Label("Welcome, " + username);
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        BorderPane.setAlignment(header, Pos.CENTER);
        view.setTop(header);

        // Left side form
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setPrefWidth(300);

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        TextField depositField = new TextField();
        depositField.setPromptText("Initial Deposit");

        ComboBox<String> accountTypeBox = new ComboBox<>();
        accountTypeBox.getItems().addAll("Savings", "Investment", "Cheque");
        accountTypeBox.getSelectionModel().selectFirst();

        Button createButton = new Button("Create Account");

        form.getChildren().addAll(
                new Label("Create New Account"),
                firstNameField, lastNameField, addressField,
                depositField, accountTypeBox, createButton
        );

        view.setLeft(form);

        // Center output
        TextArea output = new TextArea();
        output.setEditable(false);
        output.setWrapText(true);
        output.setPrefWidth(400);
        view.setCenter(output);

        // Bottom actions
        HBox actions = new HBox(10);
        actions.setPadding(new Insets(15));
        actions.setAlignment(Pos.CENTER);

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        ComboBox<String> actionBox = new ComboBox<>();
        actionBox.getItems().addAll("Deposit", "Withdraw", "Apply Interest", "View Balances");
        actionBox.getSelectionModel().selectFirst();

        Button executeButton = new Button("Execute");
        actions.getChildren().addAll(actionBox, amountField, executeButton);
        view.setBottom(actions);

        // Event: create account
        createButton.setOnAction(e -> {
            try {
                String first = firstNameField.getText().trim();
                String last = lastNameField.getText().trim();
                String address = addressField.getText().trim();
                BigDecimal deposit = new BigDecimal(depositField.getText().trim());
                String type = accountTypeBox.getValue();

                LoginController loginController = new LoginController();
                currentCustomer = loginController.createCustomer(first, last, address);

                Account account = switch (type) {
                    case "Savings" -> new SavingsAccount(currentCustomer, deposit, "Main Branch", System.currentTimeMillis());
                    case "Investment" -> new InvestmentAccount(currentCustomer, deposit, "Main Branch", System.currentTimeMillis());
                    case "Cheque" -> new ChequeAccount(currentCustomer, deposit, "Main Branch", System.currentTimeMillis(),
                            "BAC University", "Gaborone, Botswana");
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                };

                accountController.createAccountForCustomer(currentCustomer.getId(), account);
                output.appendText("Account created for " + first + " " + last + " (" + type + ")\n");
            } catch (Exception ex) {
                showError(ex);
            }
        });

        // Event: execute transaction
        executeButton.setOnAction(e -> {
            if (currentCustomer == null) {
                showAlert("No customer created yet!");
                return;
            }
            try {
                String action = actionBox.getValue();
                List<Account> accounts = accountController.getAccountsForCustomer(currentCustomer.getId());
                if (accounts.isEmpty()) {
                    showAlert("No accounts available!");
                    return;
                }

                Account acc = accounts.get(accounts.size() - 1);
                switch (action) {
                    case "Deposit" -> {
                        BigDecimal amt = new BigDecimal(amountField.getText().trim());
                        accountController.deposit(acc.getAccountNumber(), amt);
                        output.appendText("Deposited BWP " + amt + " | New Balance: " + acc.getBalance() + "\n");
                    }
                    case "Withdraw" -> {
                        BigDecimal amt = new BigDecimal(amountField.getText().trim());
                        accountController.withdraw(acc.getAccountNumber(), amt);
                        output.appendText("Withdrawn BWP " + amt + " | New Balance: " + acc.getBalance() + "\n");
                    }
                    case "Apply Interest" -> {
                        BigDecimal interest = accountController.applyInterest(acc.getAccountNumber());
                        output.appendText("Interest Applied: BWP " + interest + "\n");
                    }
                    case "View Balances" -> {
                        output.appendText("Current Balances:\n");
                        for (Account a : accounts) {
                            output.appendText(a.getClass().getSimpleName() + ": BWP " + a.getBalance() + "\n");
                        }
                    }
                }
            } catch (Exception ex) {
                showError(ex);
            }
        });
    }

    public Pane getView() {
        return view;
    }

    private void showError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}
