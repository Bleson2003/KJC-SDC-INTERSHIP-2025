package ordertracking.features;

import com.mongodb.client.*;
import org.bson.Document;
import ordertracking.db.MongoConnection;

public class GetOrderHistory {
    public static void main(String[] args) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> orders = db.getCollection("orders");

        FindIterable<Document> userOrders = orders.find(new Document("userId", "user123"));

        for (Document doc : userOrders) {
            System.out.println(doc.toJson());
        }
    }
}
