package ap.restaurant.entities;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int userId;
    private LocalDateTime createdAt;
    private int totalPrice;

    public Order() {}

    public Order(int id, int userId, LocalDateTime createdAt, int totalPrice) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.totalPrice = totalPrice;
    }

    public Order(int userId, int totalPrice) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
}
