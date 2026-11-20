package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

// a shared in-memory database for the entire app
public class StoreData {
    private static final ObservableList<Ingredient> inventoryList = FXCollections.observableArrayList();
    private static final ArrayList<Sandwich> orderHistory = new ArrayList<>();

    //shows default/preload menu items
    static {
        inventoryList.add(new ProteinIngredient("Bacon", 20, 45.00, "/images/bacon.png"));
        inventoryList.add(new ProteinIngredient("Ham", 20, 40.00, "/images/ham.png"));
        inventoryList.add(new VeggieIngredient("Lettuce", 30, 15.00, "/images/lettuce.png"));
        inventoryList.add(new VeggieIngredient("Tomato", 30, 15.00, "/images/tomato.png"));
        inventoryList.add(new SauceCheeseIngredient("Cheddar Cheese", 25, 25.00, "/images/cheese.png"));
        inventoryList.add(new SauceCheeseIngredient("Mayonnaise", 30, 10.00, "/images/mayo.png"));
    }

    // like a normal ArrayList but with an auto-refresh whenever InventoryController adds or deletes
    public static ObservableList<Ingredient> getInventoryList() {
        return inventoryList;
    }

    // a log of sandwich order during the session
    public static ArrayList<Sandwich> getOrderHistory() {
        return orderHistory;
    }

    //this saves the complete orders
    public static void addOrder(Sandwich sandwich) {
        // doesn't allow null orders to be recorded
        if (sandwich != null) {
            orderHistory.add(sandwich);
        }
    }
}