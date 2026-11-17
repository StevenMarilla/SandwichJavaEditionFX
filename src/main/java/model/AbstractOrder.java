package model;

public abstract class AbstractOrder {
    private String orderId;

    public AbstractOrder(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty.");
        }
        this.orderId = orderId.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public abstract double calculateTotal();
    public abstract String generateReceipt();
}