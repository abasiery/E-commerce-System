public class ElectronicsProduct extends Product {
    private double deviceWeight;
    
    public ElectronicsProduct(String productIdentifier, String productName, double productCost, int availableStock, double deviceWeight) {
        super(productIdentifier, productName, productCost, availableStock);
        this.deviceWeight = deviceWeight;
    }
    
    @Override
    public boolean hasExpirationDate() {
        return false;
    }
    
    @Override
    public boolean needsPhysicalDelivery() {
        return true;
    }
    
    @Override
    public double getShippingWeight() {
        return deviceWeight;
    }
    
    public void setDeviceWeight(double deviceWeight) {
        this.deviceWeight = deviceWeight;
    }
} 