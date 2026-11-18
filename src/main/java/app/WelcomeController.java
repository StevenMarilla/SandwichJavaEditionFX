package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WelcomeController {

    @FXML
    private void handleCreateSandwich(ActionEvent event) {
        Main.switchScene(event, "/app/builder-view.fxml");
    }

    @FXML
    private void handleManageIngredients(ActionEvent event) {
        Main.switchScene(event, "/app/inventory-view.fxml");
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Platform.exit();
    }
}
