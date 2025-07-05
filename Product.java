import java.time.LocalDate;

public abstract class Product {
    private String productIdentifier;
    private String productName;
    private double productCost;
    private int availableStock;

    public Product() {
        super();
    }
    
    public Product(String productIdentifier, String productName, double productCost, int availableStock) {
        super();
        this.productIdentifier = productIdentifier;
        this.productName = productName;
        this.productCost = productCost;
        this.availableStock = availableStock;
    }

    public String getProductIdentifier() {
        return productIdentifier;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductCost() {
        return productCost;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setProductIdentifier(String productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }

    // Abstract methods for different product types
    public abstract boolean hasExpirationDate();
    public abstract boolean needsPhysicalDelivery();
    public abstract double getShippingWeight(); // Returns 0.0 for non-shippable items
    
    public void showProductDetails() {
        System.out.println("Product{" + 
            "productIdentifier='" + productIdentifier + '\'' + 
            ", productName='" + productName + '\'' + 
            ", productCost=" + productCost + 
            ", availableStock=" + availableStock + 
            ", hasExpirationDate=" + hasExpirationDate() +
            ", needsPhysicalDelivery=" + needsPhysicalDelivery() +
            ", shippingWeight=" + getShippingWeight() + "kg" +
            '}');
    }
}
