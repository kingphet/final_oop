import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private String invoiceId;
    private Customer customer;
    private List<InvoiceItem> items;
    private Date date;
    private double totalAmount;
    private static final double TAX_RATE = 0.10;

    public Invoice(String invoiceId, Customer customer) {
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.date = new Date();
    }

    public void addItem(Product product, int quantity) {
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        
        InvoiceItem item = new InvoiceItem(product, quantity);
        items.add(item);
        product.updateStock(-quantity);
        calculateTotal();
    }

    public void removeItem(InvoiceItem item) {
        items.remove(item);
        item.getProduct().updateStock(item.getQuantity());
        calculateTotal();
    }

    public void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(InvoiceItem::getSubtotal)
                .sum();
        this.totalAmount += calculateTax();
    }

    public double calculateTax() {
        return items.stream()
                .mapToDouble(InvoiceItem::getSubtotal)
                .sum() * TAX_RATE;
    }

    // Getters
    public String getInvoiceId() { return invoiceId; }
    public Customer getCustomer() { return customer; }
    public List<InvoiceItem> getItems() { return new ArrayList<>(items); }
    public Date getDate() { return date; }
    public double getTotalAmount() { return totalAmount; }
}