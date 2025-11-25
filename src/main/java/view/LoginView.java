package view;

import controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginView {

    private Parent view;

    public LoginView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            this.view = loader.load();
            LoginController controller = loader.getController();
            controller.setPrimaryStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getView() {
        return view;
    }
}
