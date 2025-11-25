package controller;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private UserDAO userDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
    }

    @FXML
    public void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            User user = userDAO.findByUsername(username);
            if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
                messageLabel.setText("Login successful!");
                // Proceed to the main application
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("An error occurred. Please try again.");
        }
    }
}
