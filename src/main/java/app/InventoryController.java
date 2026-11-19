package app;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import model.Ingredient;
import model.ProteinIngredient;
import model.VeggieIngredient;
import model.SauceCheeseIngredient;
import model.StoreData;


public class InventoryController {
    @FXML
    private TableView<Ingredient> tblInventory;
    @FXML
    private TableColumn<Ingredient, String> colName;
    @FXML
    private TableColumn<Ingredient, Integer> colStock;
    @FXML
    private TableColumn<Ingredient, Double> colPrice;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtPrice;
    @FXML
    private ComboBox<String> cmbIngredientType;


    @FXML
    public void initialize() {
        cmbIngredientType.getItems().addAll("Protein", "Vegetable", "Sauce / Cheese");


        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colPrice.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().calculatePrice()).asObject());
        colStock.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getStockQuantity()).asObject());


        tblInventory.setItems(StoreData.getInventoryList());
        tblInventory.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtName.setText(newSel.getName());
                txtStock.setText(String.valueOf(newSel.getStockQuantity()));
                txtPrice.setText(String.valueOf(newSel.calculatePrice()));
            }
        });}


    @FXML
    private void handleAddIngredient(ActionEvent event) {
        String name = txtName.getText().trim();
        String stockText = txtStock.getText().trim();
        String priceText = txtPrice.getText().trim();
        String type = cmbIngredientType.getValue();


        if (name.isEmpty() || stockText.isEmpty() || priceText.isEmpty() || type == null) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return;
        }


        int stock;
        double price;


        try {
            stock = Integer.parseInt(stockText);
            price = Double.parseDouble(priceText);


            if (stock < 0 || price < 0 || !Double.isFinite(price)) {
                showAlert(Alert.AlertType.WARNING, "Stock and price must be valid non-negative numbers.");
                return;
            }


            Ingredient ingredient;
            if (type.equals("Protein")) {
                ingredient = new ProteinIngredient(name, stock, price, "/images/default.png");


            } else if (type.equals("Vegetable")) {
                ingredient = new VeggieIngredient(name, stock, price, "/images/default.png");


            } else {
                ingredient = new SauceCheeseIngredient(name, stock, price, "/images/default.png");
            }


            StoreData.getInventoryList().add(ingredient);
            clearFields();


        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Please enter valid numbers for stock and price.");


        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, e.getMessage());
        }
    }


    @FXML
    private void handleUpdateIngredient() {
        Ingredient selectedIngredient = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedIngredient == null) {
            showAlert(Alert.AlertType.WARNING, "Please select an ingredient first.");
            return;
        }


        String name = txtName.getText().trim();
        String stockText = txtStock.getText().trim();
        String priceText = txtPrice.getText().trim();


        if (name.isEmpty() || stockText.isEmpty() || priceText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return;
        }


        int stock;
        double price;


        try {
            stock = Integer.parseInt(stockText);
            price = Double.parseDouble(priceText);


            if (stock < 0 || price < 0) {
                showAlert(Alert.AlertType.WARNING, "Stock and price cannot be negative.");
                return;
            }


            selectedIngredient.setName(name);
            selectedIngredient.setStockQuantity(stock);
            selectedIngredient.setBasePrice(price);


            tblInventory.refresh();
            clearFields();


        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Please enter valid numbers for stock and price.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, e.getMessage());
        }
    }


    @FXML
    private void handleDeleteIngredient() {
        Ingredient selectedIngredient = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedIngredient == null) {
            return;
        }
        StoreData.getInventoryList().remove(selectedIngredient);
        clearFields();
    }


    @FXML
    private void handleBackToMenu(ActionEvent event) {
        Main.switchScene(event, "/app/welcome-view.fxml");
    }


    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void clearFields() {
        txtName.clear();
        txtStock.clear();
        txtPrice.clear();
        cmbIngredientType.getSelectionModel().clearSelection();
    }
}


