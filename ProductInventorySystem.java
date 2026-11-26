
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;

public class ProductInventorySystem {

    static Scanner sc = new Scanner(System.in);

    // Required collections
    static HashSet<String> categories = new HashSet<>();
    static TreeSet<String> sortedCategories = new TreeSet<>();
    static LinkedHashSet<String> recentProducts = new LinkedHashSet<>();
    static HashMap<Integer, Integer> inventory = new HashMap<>();

    // Additional maps to store product details
    static HashMap<Integer, String> productNames = new HashMap<>();
    static HashMap<Integer, String> productCategories = new HashMap<>();
    static HashMap<Integer, Double> productPrices = new HashMap<>();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n========================================");
            System.out.println("       PRODUCT INVENTORY SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add Product");
            System.out.println("2. Display All Products");
            System.out.println("3. Search Product");
            System.out.println("4. Update Product Quantity");
            System.out.println("5. Sell Product");
            System.out.println("6. Remove Product");
            System.out.println("7. Add Product Category");
            System.out.println("8. Display Unique Categories");
            System.out.println("9. Display Sorted Categories");
            System.out.println("10. Display Recently Added Products");
            System.out.println("11. Generate Inventory Report");
            System.out.println("12. Exit");
            System.out.print("Enter Your Choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    displayProducts();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    updateQuantity();
                    break;
                case 5:
                    sellProduct();
                    break;
                case 6:
                    removeProduct();
                    break;
                case 7:
                    addCategory();
                    break;
                case 8:
                    displayCategories();
                    searchCategory();
                    break;
                case 9:
                    displaySortedCategories();
                    break;
                case 10:
                    displayRecentProducts();
                    break;
                case 11:
                    generateReport();
                    break;
                case 12:
                    System.out.println("Thank you for using Product Inventory System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 12);

        sc.close();
    }

    // Safely read an integer
    static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid integer: ");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    // 1. Add Product
    static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (inventory.containsKey(id)) {
            System.out.println("Product ID already exists!");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        System.out.print("Enter Product Category: ");
        String category = sc.nextLine().trim();

        if (category.isEmpty()) {
            System.out.println("Category cannot be empty.");
            return;
        }

        System.out.print("Enter Product Price: ");
        double price = readDouble();

        if (price < 0) {
            System.out.println("Price cannot be negative.");
            return;
        }

        System.out.print("Enter Available Quantity: ");
        int quantity = readInt();

        if (quantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }

        productNames.put(id, name);
        productCategories.put(id, category);
        productPrices.put(id, price);
        inventory.put(id, quantity);

        categories.add(category);
        recentProducts.add(name);

        System.out.println("Product Added Successfully!");
    }

    static double readDouble() {
        while (!sc.hasNextDouble()) {
            System.out.print("Please enter a valid price: ");
            sc.next();
        }
        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }

    // 2. Display All Products
    static void displayProducts() {
        if (inventory.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("\n---------- PRODUCT INVENTORY ----------");
        System.out.printf("%-10s %-18s %-18s %-12s %-10s%n",
                "ID", "Product", "Category", "Price", "Quantity");

        for (Map.Entry<Integer, Integer> entry : inventory.entrySet()) {
            int id = entry.getKey();

            System.out.printf("%-10d %-18s %-18s %-12.2f %-10d%n",
                    id,
                    productNames.get(id),
                    productCategories.get(id),
                    productPrices.get(id),
                    entry.getValue());
        }

        System.out.println("Total Number of Products: " + inventory.size());
    }

    // 3. Search Product
    static void searchProduct() {
        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (inventory.containsKey(id)) {
            System.out.println("\nProduct Found!");
            System.out.println("Product ID: " + id);
            System.out.println("Product Name: " + productNames.get(id));
            System.out.println("Category: " + productCategories.get(id));
            System.out.printf("Price: %.2f%n", productPrices.get(id));
            System.out.println("Available Quantity: " + inventory.get(id));
        } else {
            System.out.println("Product not found.");
        }
    }

    // 4. Update Product Quantity (add new stock)
    static void updateQuantity() {
        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter Quantity to Add: ");
        int quantity = readInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        int current = inventory.get(id);
        inventory.put(id, current + quantity);

        System.out.println("Stock Updated Successfully!");
        System.out.println("Product: " + productNames.get(id));
        System.out.println("Available Quantity: " + inventory.get(id));
    }

    // 5. Sell Product
    static void sellProduct() {
        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter Quantity Sold: ");
        int sold = readInt();

        if (sold <= 0) {
            System.out.println("Sale quantity must be greater than zero.");
            return;
        }

        int available = inventory.get(id);

        if (sold > available) {
            System.out.println("Insufficient stock! Sale cannot be completed.");
            return;
        }

        inventory.put(id, available - sold);

        System.out.println("Sale Completed Successfully!");
        System.out.println("Product ID: " + id);
        System.out.println("Remaining Quantity: " + inventory.get(id));
    }

    // 6. Remove Product
    static void removeProduct() {
        System.out.print("Enter Product ID to Remove: ");
        int id = readInt();

        if (!inventory.containsKey(id)) {
            System.out.println("Product not found.");
            return;
        }

        String removedName = productNames.get(id);

        inventory.remove(id);
        productNames.remove(id);
        productCategories.remove(id);
        productPrices.remove(id);

        // Keep the recent-products set accurate if another
        // product record still uses the same name.
        if (!productNames.containsValue(removedName)) {
            recentProducts.remove(removedName);
        }

        // Rebuild categories from the remaining products.
        categories.clear();
        categories.addAll(productCategories.values());

        System.out.println("Product Removed Successfully!");
    }

    // 7. Add Product Category
    static void addCategory() {
        System.out.print("Enter Category Name: ");
        String category = sc.nextLine().trim();

        if (category.isEmpty()) {
            System.out.println("Category cannot be empty.");
            return;
        }

        if (categories.add(category)) {
            System.out.println("Category Added Successfully!");
        } else {
            System.out.println("Category already exists.");
        }
    }

    // 8. Display Unique Categories
    static void displayCategories() {
        System.out.println("\n--------- PRODUCT CATEGORIES ---------");

        if (categories.isEmpty()) {
            System.out.println("No categories available.");
        } else {
            for (String category : categories) {
                System.out.println(category);
            }
        }

        System.out.println("Total Unique Categories: " + categories.size());
    }

    // Search for a category
    static void searchCategory() {
        System.out.print("Enter category to search (or press Enter to skip): ");
        String category = sc.nextLine().trim();

        if (category.isEmpty()) {
            return;
        }

        if (categories.contains(category)) {
            System.out.println("Category found: " + category);
        } else {
            System.out.println("Category not found.");
        }
    }

    // 9. Display Sorted Categories
    static void displaySortedCategories() {
        sortedCategories.clear();
        sortedCategories.addAll(categories);

        System.out.println("\n--------- SORTED CATEGORIES ----------");

        if (sortedCategories.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }

        for (String category : sortedCategories) {
            System.out.println(category);
        }

        System.out.println("First Category: " + sortedCategories.first());
        System.out.println("Last Category: " + sortedCategories.last());

        System.out.println("\nCategories in Descending Order:");
        System.out.println(sortedCategories.descendingSet());
    }

    // 10. Display Recently Added Products
    static void displayRecentProducts() {
        System.out.println("\n------ RECENTLY ADDED PRODUCTS ------");

        if (recentProducts.isEmpty()) {
            System.out.println("No recent products available.");
            return;
        }

        for (String name : recentProducts) {
            System.out.println(name);
        }

        System.out.print("Enter product name to remove from this list "
                + "(or press Enter to skip): ");
        String name = sc.nextLine().trim();

        if (!name.isEmpty()) {
            if (recentProducts.remove(name)) {
                System.out.println("Product removed from recent-products list.");
            } else {
                System.out.println("Product not found in recent-products list.");
            }
        }
    }

    // 11. Generate Inventory Report
    static void generateReport() {
        System.out.println("\n========== INVENTORY REPORT ==========");

        if (inventory.isEmpty()) {
            System.out.println("No products available.");
            System.out.println("Total Product Units Available: 0");
            System.out.println("Total Unique Categories: " + categories.size());
            return;
        }

        int totalUnits = 0;
        int highestQuantity = -1;
        int highestId = -1;

        System.out.println("\nAll Products:");
        for (Map.Entry<Integer, Integer> entry : inventory.entrySet()) {
            int id = entry.getKey();
            int quantity = entry.getValue();

            System.out.println(productNames.get(id)
                    + " (ID: " + id + "), Quantity: " + quantity);

            totalUnits += quantity;

            if (quantity > highestQuantity) {
                highestQuantity = quantity;
                highestId = id;
            }
        }

        System.out.println("\nProducts with Quantity Below 5:");
        boolean found = false;

        for (Map.Entry<Integer, Integer> entry : inventory.entrySet()) {
            if (entry.getValue() < 5) {
                int id = entry.getKey();
                System.out.println(productNames.get(id)
                        + " (ID: " + id + "), Quantity: " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
            System.out.println("None");
        }

        System.out.println("\nTotal Product Units Available: " + totalUnits);

        System.out.println("Product with Highest Available Quantity: "
                + productNames.get(highestId));
        System.out.println("Highest Available Quantity: " + highestQuantity);

        System.out.println("\nAll Unique Categories: " + categories);
        System.out.println("Total Unique Categories: " + categories.size());

        System.out.println("======================================");
    }
}
