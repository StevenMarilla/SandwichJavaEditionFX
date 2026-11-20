package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Ingredient extends MenuItem {

    private final IntegerProperty stock;
    private String imagePath;

    public Ingredient(String name, int stock, double price, String imagePath) {
        super(name, price);
        this.stock = new SimpleIntegerProperty();
        setStock(stock);
        setImagePath(imagePath);
    }

    public int getStock() {
        return stock.get();
    }

    public int getStockQuantity() {
        return stock.get();
    }

    public IntegerProperty stockProperty() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }
        this.stock.set(stock);
    }

    public void setStockQuantity(int stock) {
        setStock(stock);
    }

    public void deductStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deduction amount must be positive.");
        }
        if (amount > getStock()) {
            throw new IllegalArgumentException("Insufficient stock for " + getName());
        }
        setStock(getStock() - amount);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Image path cannot be empty.");
        }
        this.imagePath = imagePath.trim();
    }

    public abstract String getPreparationNote();
}