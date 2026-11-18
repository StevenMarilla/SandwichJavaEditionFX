package model;

import java.util.ArrayList;

public class Sandwich extends AbstractOrder {
    private ArrayList<Ingredient> ingredients;

    public Sandwich() {
        super("ORD-" + (StoreData.getOrderHistory().size() + 1001));
        this.ingredients = new ArrayList<>();
    }

    public Sandwich(String orderId) {
        super(orderId);
        this.ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Cannot add a null ingredient.");
        }
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void clearIngredients() {
        ingredients.clear();
    }

    @Override
    public double calculateTotal() {
        double total = 0.0;
        for (Ingredient item : ingredients) {
            total += item.calculatePrice(); // Polymorphic call
        }
        return total;
    }

    @Override
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order ID: ").append(getOrderId()).append("\n");
        receipt.append("----------------------------\n");
        for (Ingredient item : ingredients) {
            receipt.append("- ").append(item.getSummary()).append("\n"); // Polymorphic call
        }
        receipt.append("----------------------------\n");
        receipt.append(String.format("Total: PHP %.2f", calculateTotal()));
        return receipt.toString();
    }
}