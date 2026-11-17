package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class StoreData {
    private static final ObservableList<Ingredient> inventoryList = FXCollections.observableArrayList();
    private static final ArrayList<Sandwich> orderHistory = new ArrayList<>();

    public static ObservableList<Ingredient> getInventoryList() {
        return inventoryList;
    }

    public static ArrayList<Sandwich> getOrderHistory() {
        return orderHistory;
    }

    public static void addOrder(Sandwich sandwich) {
        if (sandwich != null) {
            orderHistory.add(sandwich);
        }
    }
}