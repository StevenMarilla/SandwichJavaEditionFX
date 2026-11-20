package model;

public abstract class MenuItem {
    private String name;
    private double basePrice;

    // this the parameterized constructor for MenuItem
    public MenuItem(String name, double basePrice) {
        // setters are called to protect the validation during when it's first created
        // and whenever it's updated
        setName(name);
        setBasePrice(basePrice);
    }

    // allows other parts of the program to read the item's name without having direct access
    public String getName() {
        return name;
    }

    // update the item's name while enforcing data validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        this.name = name.trim();
    }

    // returns stored BasePrice
    public double getBasePrice() {
        return basePrice;
    }

    // ensures BasePrice can't be below 0 or negative
    public void setBasePrice(double basePrice) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative.");
        }
        this.basePrice = basePrice;
    }
    // abstract methods for subclasses which has a specific behavior
    public abstract double calculatePrice();
    public abstract String getSummary();
}