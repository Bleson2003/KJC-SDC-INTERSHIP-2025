package ordertracking.features;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ordertracking.db.MongoConnection;
import ordertracking.model.Order;
import org.bson.Document;

import java.util.*;

public class PlaceOrder {
    public static void main(String[] args) {
        // Step 1: Create product catalog
        Map<String, Map<String, Object>> products = new HashMap<>();

        products.put("P1001", Map.of("name", "Wireless Mouse", "price", 499.0));
        products.put("P1002", Map.of("name", "Mechanical Keyboard", "price", 1499.0));
        products.put("P1003", Map.of("name", "Gaming Headset", "price", 1999.0));
        products.put("P1004", Map.of("name", "USB-C Charger", "price", 899.0));

        // Step 2: Display items
        System.out.println("📦 Available Products:");
        for (Map.Entry<String, Map<String, Object>> entry : products.entrySet()) {
            String itemId = entry.getKey();
            String name = (String) entry.getValue().get("name");
            double price = (Double) entry.getValue().get("price");
            System.out.println(itemId + " - " + name + " - ₹" + price);
        }

        // Step 3: Get user input
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter your User ID: ");
        String userId = sc.nextLine();

        System.out.print("Enter Item ID to purchase: ");
        String itemId = sc.nextLine().toUpperCase();

        if (!products.containsKey(itemId)) {
            System.out.println("❌ Invalid Item ID.");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine(); // consume leftover newline

        // Step 4: Create order object
        String orderId = UUID.randomUUID().toString();
        String orderDate = java.time.LocalDate.now().toString();
        String status = "Placed";
        double price = (Double) products.get(itemId).get("price");

        Order order = new Order(orderId, userId, itemId, quantity, price, status, orderDate);

        // Step 5: Save to DB
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> orders = db.getCollection("orders");

        Document doc = new Document("orderId", order.orderId)
                .append("userId", order.userId)
                .append("productId", order.productId)
                .append("quantity", order.quantity)
                .append("price", order.price)
                .append("status", order.status)
                .append("orderDate", order.orderDate);

        orders.insertOne(doc);

        // Step 6: Show order confirmation
        System.out.println("\n✅ Order placed successfully!");
        System.out.println("🔍 Order Summary:");
        System.out.println("Order ID: " + order.orderId);
        System.out.println("Product: " + products.get(itemId).get("name"));
        System.out.println("Quantity: " + quantity);
        System.out.println("Total Price: ₹" + (quantity * price));
        System.out.println("Status: " + status);
        System.out.println("Track this order using the Order ID.\n");
    }
}
