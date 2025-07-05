import java.time.LocalDate;

public class NonShippablePerishableProduct extends Product {
    private LocalDate expiryDate;
    
    public NonShippablePerishableProduct(String id, String name, double price, int quantity, LocalDate expiryDate) {
        super(id, name, price, quantity);
        this.expiryDate = expiryDate;
    }
    
    @Override
    public boolean hasExpirationDate() {
        return true;
    }
    
    @Override
    public boolean needsPhysicalDelivery() {
        return false;
    }
    
    @Override
    public double getShippingWeight() {
        return 0.0; // Non-shippable items have no weight
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
    
    public boolean isPastExpirationDate() {
        return LocalDate.now().isAfter(expiryDate);
    }
    
    @Override
    public void showProductDetails() {
        System.out.println("Product{" + 
            "productIdentifier='" + getProductIdentifier() + '\'' + 
            ", productName='" + getProductName() + '\'' + 
            ", productCost=" + getProductCost() + 
            ", availableStock=" + getAvailableStock() + 
            ", hasExpirationDate=" + hasExpirationDate() +
            ", needsPhysicalDelivery=" + needsPhysicalDelivery() +
            ", shippingWeight=" + getShippingWeight() + "kg" +
            ", expiryDate=" + expiryDate +
            ", expired=" + isExpired() +
            '}');
    }
} 