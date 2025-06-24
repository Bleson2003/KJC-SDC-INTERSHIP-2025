import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

public class EmployeePortal {
    private static final String DB_NAME = "employee_db";
    private static final String COLLECTION_NAME = "employees";
    private static final Scanner scanner = new Scanner(System.in);
    private static final MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private static final MongoCollection<Document> collection = mongoClient
            .getDatabase(DB_NAME)
            .getCollection(COLLECTION_NAME);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nEMPLOYEE MANAGEMENT PORTAL");
            System.out.println("Enter the choice:");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Search/Filter Employees");
            System.out.println("5. Department Aggregation");
            System.out.println("6. Show All Employees");
            System.out.println("7. Exit");

            switch (scanner.nextLine()) {
                case "1" -> addEmployee();
                case "2" -> updateEmployee();
                case "3" -> deleteEmployee();
                case "4" -> searchEmployees();
                case "5" -> departmentAggregation();
                case "6" -> showAllEmployees();
                case "7" -> {
                    mongoClient.close();
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void addEmployee() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Place: ");
        String place = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();

        Document employee = new Document("name", name)
                .append("place", place)
                .append("age", age)
                .append("department", dept);

        collection.insertOne(employee);
        System.out.println("Employee added successfully.");
    }

    private static void updateEmployee() {
        System.out.print("Enter Employee Name to Update: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Place: ");
        String newPlace = scanner.nextLine();

        Document update = new Document("$set", new Document("place", newPlace));
        collection.updateOne(new Document("name", name), update);
        System.out.println("Employee updated.");
    }

    private static void deleteEmployee() {
        System.out.print("Enter Employee Name to Delete: ");
        String name = scanner.nextLine();

        collection.deleteOne(new Document("name", name));
        System.out.println("Employee deleted.");
    }

    private static void searchEmployees() {
        System.out.print("Enter field to search (name/place/age/department): ");
        String field = scanner.nextLine();
        System.out.print("Enter value: ");
        String value = scanner.nextLine();

        FindIterable<Document> results;
        if (field.equals("age")) {
            results = collection.find(new Document(field, Integer.parseInt(value)));
        } else {
            results = collection.find(new Document(field, value));
        }

        System.out.println("\nMatching Employees:");
        for (Document doc : results) {
            System.out.println(doc.toJson());
        }
    }

    private static void departmentAggregation() {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.group("$department", Accumulators.sum("count", 1))
        );
        AggregateIterable<Document> result = collection.aggregate(pipeline);
        System.out.println("\nDepartment-wise Employee Count:");
        for (Document doc : result) {
            System.out.printf("Department: %s - Count: %d\n", doc.getString("_id"), doc.getInteger("count"));
        }
    }

    private static void showAllEmployees() {
        FindIterable<Document> employees = collection.find();
        System.out.println("\nAll Employees:");
        for (Document doc : employees) {
            String name = doc.getString("name");
            String place = doc.getString("place");
            int age = doc.getInteger("age", 0);
            String dept = doc.getString("department");

            System.out.printf("Name: %s | Place: %s | Age: %d | Department: %s\n", name, place, age, dept);
        }
    }

}


