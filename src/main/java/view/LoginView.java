package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {
    private final VBox view;
    private final LoginController loginController = new LoginController();

    public LoginView(Stage stage) {
        view = new VBox(15);
        view.setPadding(new Insets(25));
        view.setAlignment(Pos.CENTER);

        Label title = new Label("Ngoni Banking System");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (loginController.authenticate(user, pass)) {
                messageLabel.setText("Login successful!");
                AccountView accountView = new AccountView(stage, user);
                stage.setScene(new Scene(accountView.getView(), 800, 600));
            } else {
                messageLabel.setText("Invalid credentials.");
            }
        });

        view.getChildren().addAll(title, usernameField, passwordField, loginButton, messageLabel);
    }

    public VBox getView() {
        return view;
    }
}
