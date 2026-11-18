package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.Ingredient;
import model.Sandwich;
import model.StoreData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BuilderController {

    @FXML
    private FlowPane paneIngredients;

    @FXML
    private VBox boxSandwichStack;

    @FXML
    private ImageView imgTopBread;

    @FXML
    private ImageView imgBottomBread;

    @FXML
    private Label lblSummary;

    @FXML
    private Label lblGrandTotal;

    private Sandwich currentSandwich = new Sandwich();
    private final Map<Ingredient, ImageView> layerMap = new HashMap<>();

    @FXML
    public void initialize() {
        boxSandwichStack.setSpacing(-15);

        for (Ingredient item : StoreData.getInventoryList()) {
            ToggleButton toggleBtn = new ToggleButton(
                    String.format("%s (PHP %.2f)", item.getName(), item.calculatePrice())
            );

            if (item.getStockQuantity() <= 0) {
                toggleBtn.setDisable(true);
            }

            toggleBtn.setOnAction(event -> {
                if (toggleBtn.isSelected()) {
                    currentSandwich.addIngredient(item);

                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(item.getImagePath())));
                    ImageView layerView = new ImageView(image);
                    layerView.setFitWidth(200);
                    layerView.setPreserveRatio(true);

                    layerMap.put(item, layerView);

                    int insertIndex = boxSandwichStack.getChildren().size() - 1;
                    boxSandwichStack.getChildren().add(insertIndex, layerView);
                } else {
                    currentSandwich.removeIngredient(item);

                    ImageView layerView = layerMap.remove(item);
                    if (layerView != null) {
                        boxSandwichStack.getChildren().remove(layerView);
                    }
                }
                updateSummaryAndTotal();
            });

            paneIngredients.getChildren().add(toggleBtn);
        }

        updateSummaryAndTotal();
    }

    private void updateSummaryAndTotal() {
        lblSummary.setText(currentSandwich.getIngredients().size() + " ingredients selected");
        lblGrandTotal.setText(String.format("Total: PHP %.2f", currentSandwich.calculateTotal()));
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        currentSandwich.clearIngredients();
        Main.switchScene(event, "/app/welcome-view.fxml");
    }

    @FXML
    private void handleConfirm(ActionEvent event) {
        for (Ingredient item : currentSandwich.getIngredients()) {
            item.deductStock(1);
        }

        StoreData.addOrder(currentSandwich);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Sandwich Order Summary");
        alert.setContentText(currentSandwich.generateReceipt());
        alert.showAndWait();

        Main.switchScene(event, "/app/welcome-view.fxml");
    }
}

