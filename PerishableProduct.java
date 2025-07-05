import java.time.LocalDate;

public class PerishableProduct extends Product {
    private LocalDate bestBeforeDate;
    private double itemWeight;
    
    public PerishableProduct(String productIdentifier, String productName, double productCost, int availableStock, LocalDate bestBeforeDate, double itemWeight) {
        super(productIdentifier, productName, productCost, availableStock);
        this.bestBeforeDate = bestBeforeDate;
        this.itemWeight = itemWeight;
    }
    
    @Override
    public boolean hasExpirationDate() {
        return true;
    }
    
    @Override
    public boolean needsPhysicalDelivery() {
        return true;
    }
    
    @Override
    public double getShippingWeight() {
        return itemWeight;
    }
    
    public LocalDate getBestBeforeDate() {
        return bestBeforeDate;
    }
    
    public void setBestBeforeDate(LocalDate bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }
    
    public void setItemWeight(double itemWeight) {
        this.itemWeight = itemWeight;
    }
    
    public boolean isPastExpirationDate() {
        return LocalDate.now().isAfter(bestBeforeDate);
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
            ", bestBeforeDate=" + bestBeforeDate +
            ", isExpired=" + isPastExpirationDate() +
            '}');
    }
} 