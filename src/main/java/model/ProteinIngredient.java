package model;

public class ProteinIngredient extends Ingredient {

    private static final double PREP_FEE = 5.00;

    public ProteinIngredient(String name, int stock, double price, String imagePath) {
        super(name, stock, price, imagePath);
    }

    public String getCategory() {
        return "Protein";
    }

    @Override
    public double calculatePrice() {
        return getBasePrice() + PREP_FEE;
    }

    @Override
    public String getSummary() {
        return String.format("%s [%s] - PHP %.2f (%s)",
                getName(), getCategory(), calculatePrice(), getPreparationNote());
    }

    @Override
    public String getPreparationNote() {
        return "Grilled fresh to order";
    }
}