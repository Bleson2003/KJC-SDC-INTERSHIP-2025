package ordertracking.features;

import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Accumulators;
import org.bson.Document;
import ordertracking.db.MongoConnection;

import java.util.Arrays;

public class AggregateSales {
    public static void main(String[] args) {
        MongoDatabase db = MongoConnection.getDatabase();
        MongoCollection<Document> orders = db.getCollection("orders");

        AggregateIterable<Document> result = orders.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("productId", "prod456")),
                Aggregates.group("$productId",
                        Accumulators.sum("totalQuantity", "$quantity"),
                        Accumulators.sum("totalRevenue", new Document("$multiply", Arrays.asList("$quantity", "$price")))
                )
        ));

        for (Document doc : result) {
            System.out.println(doc.toJson());
        }
    }
}
