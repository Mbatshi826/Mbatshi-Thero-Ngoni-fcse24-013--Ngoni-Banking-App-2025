package controller;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import view.AccountView;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private UserDAO userDAO;
    private Stage primaryStage;

    public LoginController() {
        this.userDAO = new UserDAO();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            User user = userDAO.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                messageLabel.setText("Login successful!");
                showAccountView();
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("An error occurred.");
        }
    }

    private void showAccountView() {
        try {
            AccountView accountView = new AccountView(primaryStage);
            Scene scene = new Scene(accountView.getView(), 600, 400);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
