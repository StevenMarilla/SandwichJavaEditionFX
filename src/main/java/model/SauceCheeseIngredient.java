package model;

public class SauceCheeseIngredient extends Ingredient {

    public SauceCheeseIngredient(String name, int stock, double price, String imagePath) {
        super(name, stock, price, imagePath);
    }

    public String getCategory() {
        return "Sauce / Cheese";
    }

    @Override
    public double calculatePrice() {
        return getBasePrice();
    }

    @Override
    public String getSummary() {
        return String.format("%s [%s] - PHP %.2f (%s)",
                getName(), getCategory(), calculatePrice(), getPreparationNote());
    }

    @Override
    public String getPreparationNote() {
        return "Rich & creamy dressing/topping";
    }
}