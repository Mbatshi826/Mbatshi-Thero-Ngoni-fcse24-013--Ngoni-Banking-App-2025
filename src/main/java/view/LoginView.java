package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView extends Application {

    private LoginController loginController;

    public LoginView() {
        this.loginController = new LoginController();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Username Label
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        // Username Input
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        // Password Label
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        // Password Input
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        // Login Button
        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 1, 2);

        // Error Label
        Label errorLabel = new Label();
        GridPane.setConstraints(errorLabel, 1, 3);

        loginButton.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            boolean isValid = loginController.login(username, password);
            if (isValid) {
                // Open the main application window
                App mainApp = new App();
                mainApp.start(new Stage());
                primaryStage.close();
            } else {
                errorLabel.setText("Invalid username or password.");
            }
        });

        grid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton, errorLabel);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
