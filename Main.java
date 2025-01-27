import java.util.Scanner;

public class Main {
    private static StoreManager store;
    private static Scanner scanner;

    public static void main(String[] args) {
        store = new StoreManager();
        scanner = new Scanner(System.in);
        
        while (true) {
            showMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> handleProductManagement();
                case 2 -> handleCustomerManagement();
                case 3 -> handleSales();
                case 4 -> {
                    System.out.println("Thank you for using the Store Management System!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Store Management System ===");
        System.out.println("1. Product Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Sales");
        System.out.println("4. Exit");
    }

    private static void handleProductManagement() {
        while (true) {
            System.out.println("\n=== Product Management ===");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Update Product Stock");
            System.out.println("4. Remove Product");
            System.out.println("5. Back to Main Menu");

            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewAllProducts();
                case 3 -> updateProductStock();
                case 4 -> removeProduct();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleCustomerManagement() {
        while (true) {
            System.out.println("\n=== Customer Management ===");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. View Customer Details");
            System.out.println("4. Remove Customer");
            System.out.println("5. Back to Main Menu");

            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> viewAllCustomers();
                case 3 -> viewCustomerDetails();
                case 4 -> removeCustomer();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleSales() {
        while (true) {
            System.out.println("\n=== Sales Management ===");
            System.out.println("1. Create New Invoice");
            System.out.println("2. View All Invoices");
            System.out.println("3. Back to Main Menu");

            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> createInvoice();
                case 2 -> viewAllInvoices();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Product Management Methods
    private static void addProduct() {
        System.out.println("\n=== Add New Product ===");
        String productId = getStringInput("Enter Product ID: ");
        String name = getStringInput("Enter Product Name: ");
        double price = getDoubleInput("Enter Price: ");
        int stock = getIntInput("Enter Initial Stock: ");

        Product product = new Product(productId, name, price, stock);
        store.addProduct(product);
        System.out.println("Product added successfully!");
    }

    private static void viewAllProducts() {
        System.out.println("\n=== Product List ===");
        System.out.printf("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
        System.out.println("===========================================");
        
        for (Product product : store.getProductList()) {
            System.out.printf("%-10s %-20s %-10.2f %-10d%n",
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity()
            );
        }
    }

    private static void updateProductStock() {
        viewAllProducts();
        String productId = getStringInput("Enter Product ID to update: ");
        store.findProduct(productId).ifPresentOrElse(
            product -> {
                int quantity = getIntInput("Enter quantity to add (negative for reduction): ");
                try {
                    product.updateStock(quantity);
                    System.out.println("Stock updated successfully!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            },
            () -> System.out.println("Product not found!")
        );
    }

    private static void removeProduct() {
        viewAllProducts();
        String productId = getStringInput("Enter Product ID to remove: ");
        store.removeProduct(productId);
        System.out.println("Product removed (if it existed)!");
    }

    // Customer Management Methods
    private static void addCustomer() {
        System.out.println("\n=== Add New Customer ===");
        String customerId = getStringInput("Enter Customer ID: ");
        String name = getStringInput("Enter Customer Name: ");
        String phone = getStringInput("Enter Phone Number: ");
        String address = getStringInput("Enter Address: ");

        Customer customer = new Customer(customerId, name, phone, address);
        store.addCustomer(customer);
        System.out.println("Customer added successfully!");
    }

    private static void viewAllCustomers() {
        System.out.println("\n=== Customer List ===");
        System.out.printf("%-10s %-20s %-15s %-20s%n", "ID", "Name", "Phone", "Address");
        System.out.println("=====================================================");
        
        for (Customer customer : store.getCustomerList()) {
            System.out.printf("%-10s %-20s %-15s %-20s%n",
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getAddress()
            );
        }
    }

    private static void viewCustomerDetails() {
        viewAllCustomers();
        String customerId = getStringInput("Enter Customer ID to view details: ");
        store.findCustomer(customerId).ifPresentOrElse(
            customer -> {
                System.out.println("\n=== Customer Details ===");
                System.out.println("ID: " + customer.getId());
                System.out.println("Name: " + customer.getName());
                System.out.println("Phone: " + customer.getPhone());
                System.out.println("Address: " + customer.getAddress());
                System.out.println("Total Purchases: " + customer.getTotalPurchases());
            },
            () -> System.out.println("Customer not found!")
        );
    }

    private static void removeCustomer() {
        viewAllCustomers();
        String customerId = getStringInput("Enter Customer ID to remove: ");
        store.removeCustomer(customerId);
        System.out.println("Customer removed (if existed)!");
    }

    // Sales Management Methods
    private static void createInvoice() {
        viewAllCustomers();
        String customerId = getStringInput("Enter Customer ID for invoice: ");
        
        try {
            Invoice invoice = store.createInvoice(customerId);
            while (true) {
                viewAllProducts();
                System.out.println("\n1. Add item to invoice");
                System.out.println("2. Finish invoice");
                
                int choice = getIntInput("Enter your choice: ");
                if (choice == 2) break;
                
                if (choice == 1) {
                    String productId = getStringInput("Enter Product ID: ");
                    int quantity = getIntInput("Enter Quantity: ");
                    
                    store.findProduct(productId).ifPresentOrElse(
                        product -> {
                            try {
                                invoice.addItem(product, quantity);
                                System.out.println("Item added to invoice!");
                            } catch (IllegalArgumentException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        },
                        () -> System.out.println("Product not found!")
                    );
                }
            }
            
            // Print final invoice
            printInvoice(invoice);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewAllInvoices() {
        System.out.println("\n=== Invoice History ===");
        for (Invoice invoice : store.getInvoiceHistory()) {
            printInvoice(invoice);
            System.out.println("----------------------------------------");
        }
    }

    private static void printInvoice(Invoice invoice) {
        System.out.println("\n=== Invoice " + invoice.getInvoiceId() + " ===");
        System.out.println("Customer: " + invoice.getCustomer().getName());
        System.out.println("Date: " + invoice.getDate());
        System.out.println("\nItems:");
        System.out.printf("%-20s %-10s %-10s %-10s%n", "Product", "Quantity", "Price", "Subtotal");
        System.out.println("================================================");
        
        for (InvoiceItem item : invoice.getItems()) {
            System.out.printf("%-20s %-10d %-10.2f %-10.2f%n",
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getPrice(),
                item.getSubtotal()
            );
        }
        
        System.out.println("------------------------------------------------");
        System.out.printf("Total Amount: %.2f%n", invoice.getTotalAmount());
    }

    // Utility Methods for Input
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}