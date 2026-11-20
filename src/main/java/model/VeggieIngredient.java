package model;

public class VeggieIngredient extends Ingredient {

    public VeggieIngredient(String name, int stock, double price, String imagePath) {
        super(name, stock, price, imagePath);
    }

    public String getCategory() {
        return "Vegetable";
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
        return "Washed, crisp & chilled";
    }
}