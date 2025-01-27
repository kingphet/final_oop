public class InvoiceItem {
    private Product product;
    private int quantity;
    private double subtotal;

    public InvoiceItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        calculateSubtotal();
    }

    public void calculateSubtotal() {
        this.subtotal = product.getPrice() * quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return subtotal; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateSubtotal();
    }
}
