public class DigitalProduct extends Product {
    
    public DigitalProduct(String id, String name, double price, int quantity) {
        super(id, name, price, quantity);
    }
    
    @Override
    public boolean hasExpirationDate() {
        return false;
    }
    
    @Override
    public boolean needsPhysicalDelivery() {
        return false;
    }
    
    @Override
    public double getShippingWeight() {
        return 0.0; // Digital products have no weight
    }
} 