package model;

import java.util.ArrayList;

// Inheritance and Polymorphism applied
// Sandwich inherits AbstractOrder
public class Sandwich extends AbstractOrder {
    //it's like a basket that holds the ingredients of the subclasses
    // (ProteinIngredient, VeggieIngredient, SauceCheeseIngredient
    private ArrayList<Ingredient> ingredients;

    //a constructor with no arg
    public Sandwich() {
        // generates unique ID based on how many orders there is in StoreData
        super("ORD-" + (StoreData.getOrderHistory().size() + 1001));
        // creates an empty ArrayList
        this.ingredients = new ArrayList<>();
    }

    //constructer with a parameter
    public Sandwich(String orderId) {
        //passes a custom Order ID
        super(orderId);
        this.ingredients = new ArrayList<>();
    }

    //checks if the ingredient is not null
    public void addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Cannot add a null ingredient.");
        }
        // adds the new ingredient
        ingredients.add(ingredient);
    }

    //removes the chose ingredient
    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    // returns the list of the chosen ingredients
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    //empties the entire list when clicking cancel
    public void clearIngredients() {
        ingredients.clear();
    }

    //Polymorphism of AbstractOrder
    @Override
    public double calculateTotal() {
        double total = 0.0;
        // loops through ingredients
        for (Ingredient item : ingredients) {
            // runs specific price formula from whatever subclass then adds them up
            total += item.calculatePrice(); // Polymorphic call
        }
        return total;
    }

    //Polymorph-ed from AbstractOrder
    @Override
    public String generateReceipt() {
        //this glues multiple lines of text together
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order ID: ").append(getOrderId()).append("\n");
        receipt.append("----------------------------\n");
        // show each ingredient's custom line
        for (Ingredient item : ingredients) {
            receipt.append("- ").append(item.getSummary()).append("\n"); // Polymorphic call
        }
        receipt.append("----------------------------\n");
        // final total formatted to 2 decimal
        receipt.append(String.format("Total: PHP %.2f", calculateTotal()));
        return receipt.toString();
    }
}