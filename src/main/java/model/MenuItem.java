package model;

public abstract class MenuItem {
    private String name;
    private double basePrice;

    public MenuItem(String name, double basePrice) {
        setName(name);
        setBasePrice(basePrice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        this.name = name.trim();
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative.");
        }
        this.basePrice = basePrice;
    }

    public abstract double calculatePrice();
    public abstract String getSummary();
}