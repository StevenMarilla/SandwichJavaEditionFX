package model;

public abstract class AbstractOrder {
    private String orderId;

    public AbstractOrder(String orderId) {
        //to check if the entered value is empty
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty.");
        }
        //trims the leading and trailing whitespace
        this.orderId = orderId.trim();
    }

    //allows other parts of the program to read the OrderId's name without having direct access
    public String getOrderId() {
        return orderId;
    }

    // abstract methods for subclasses which has a specific behavior
    public abstract double calculateTotal();
    public abstract String generateReceipt();
}