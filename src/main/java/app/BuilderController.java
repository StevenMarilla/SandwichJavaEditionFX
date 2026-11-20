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
        boxSandwichStack.setSpacing(-45);

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
                    layerView.setFitWidth(160);
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
        if (currentSandwich.getIngredients().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Sandwich");
            alert.setHeaderText("No Ingredients Selected");
            alert.setContentText("Please select at least one ingredient before confirming your order.");
            alert.showAndWait();
            return;
        }

        double total = currentSandwich.calculateTotal();

        // Prompt for payment
        javafx.scene.control.TextInputDialog payDialog = new javafx.scene.control.TextInputDialog();
        payDialog.setTitle("Payment");
        payDialog.setHeaderText(String.format("Total Amount Due: PHP %.2f", total));
        payDialog.setContentText("Enter cash tendered (PHP):");

        java.util.Optional<String> result = payDialog.showAndWait();
        if (result.isEmpty()) {
            return; // User clicked Cancel on payment prompt
        }

        double amountPaid;
        try {
            amountPaid = Double.parseDouble(result.get().trim());
        } catch (NumberFormatException e) {
            Alert err = new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric payment amount.");
            err.showAndWait();
            return;
        }

        if (amountPaid < total) {
            Alert err = new Alert(Alert.AlertType.WARNING,
                    String.format("Insufficient payment. You still need PHP %.2f.", total - amountPaid));
            err.showAndWait();
            return;
        }

        double change = amountPaid - total;

        // Deduct stock & record order only after valid payment
        for (Ingredient item : currentSandwich.getIngredients()) {
            item.deductStock(1);
        }
        StoreData.addOrder(currentSandwich);

        // Display receipt with payment & change
        String fullReceipt = currentSandwich.generateReceipt()
                + String.format("\nCash Tendered: PHP %.2f\nChange: PHP %.2f", amountPaid, change);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Confirmed");
        alert.setHeaderText("Sandwich Order Summary");

        javafx.scene.control.TextArea receiptArea = new javafx.scene.control.TextArea(fullReceipt);
        receiptArea.setEditable(false);
        receiptArea.setWrapText(true);
        receiptArea.setPrefWidth(420);
        receiptArea.setPrefHeight(240);

        alert.getDialogPane().setContent(receiptArea);
        alert.showAndWait();

        Main.switchScene(event, "/app/welcome-view.fxml");
    }
}