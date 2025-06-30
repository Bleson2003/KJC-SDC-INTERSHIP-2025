package ordertracking.features;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ordertracking.db.MongoConnection;

public class UpdateOrderStatus {
    public static void main(String[] args) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> orders = db.getCollection("orders");

        String orderId = "put-your-order-id-here";  // Replace this

        orders.updateOne(
                new Document("orderId", orderId),
                new Document("$set", new Document("status", "Shipped"))
        );

        System.out.println("✅ Order status updated!");
    }
}
