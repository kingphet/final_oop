import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private List<Invoice> invoices;

    public Customer(String id, String name, String phone, String address) {
        super(id, name, phone, address);
        this.invoices = new ArrayList<>();
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public List<Invoice> getInvoices() {
        return new ArrayList<>(invoices);
    }

    public double getTotalPurchases() {
        return invoices.stream()
                .mapToDouble(Invoice::getTotalAmount)
                .sum();
    }
}
