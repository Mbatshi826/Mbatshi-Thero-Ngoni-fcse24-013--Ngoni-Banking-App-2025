package view;

import controller.AccountController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountView {

    private Parent view;

    public AccountView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/account.fxml"));
            this.view = loader.load();
            AccountController controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getView() {
        return view;
    }
}
