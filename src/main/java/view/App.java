package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Start with the login view
        LoginView loginView = new LoginView(stage);
        stage.setTitle("Ngoni Banking System");
        stage.setScene(new Scene(loginView.getView(), 400, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args); // Use JavaFX Application launch
    }
}