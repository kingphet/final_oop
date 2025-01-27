import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoreManager {
    private List<Product> products;
    private List<Customer> customers;
    private List<Invoice> invoices;

    public StoreManager() {
        this.products = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.invoices = new ArrayList<>();
    }

    // Product Management
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(String productId) {
        products.removeIf(p -> p.getProductId().equals(productId));
    }

    public Optional<Product> findProduct(String productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();
    }

    // Customer Management
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomer(String customerId) {
        customers.removeIf(c -> c.getId().equals(customerId));
    }

    public Optional<Customer> findCustomer(String customerId) {
        return customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst();
    }

    // Invoice Management
    public Invoice createInvoice(String customerId) {
        Customer customer = findCustomer(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        
        String invoiceId = "INV-" + (invoices.size() + 1);
        Invoice invoice = new Invoice(invoiceId, customer);
        invoices.add(invoice);
        customer.addInvoice(invoice);
        return invoice;
    }

    // Getters for lists
    public List<Product> getProductList() {
        return new ArrayList<>(products);
    }

    public List<Customer> getCustomerList() {
        return new ArrayList<>(customers);
    }

    public List<Invoice> getInvoiceHistory() {
        return new ArrayList<>(invoices);
    }
}