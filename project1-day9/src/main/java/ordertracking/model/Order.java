package ordertracking.model;

public class Order {
    public String orderId;
    public String userId;
    public String productId;
    public int quantity;
    public double price;
    public String status;
    public String orderDate;

    public Order(String orderId, String userId, String productId, int quantity, double price, String status, String orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
    }
}
