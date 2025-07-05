import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Shop implements InventoryService {
    private List<Product> inventoryItems;
    
    public Shop () {
        inventoryItems = new ArrayList<>();
        initializeInventory();
    }
    
    private void initializeInventory(){
        // Electronic devices (non-expirable, shippable)
        inventoryItems.add(new ElectronicsProduct("P001","Laptop",899.99,5,2.5));
        inventoryItems.add(new ElectronicsProduct("P002","Smartphone",599.99,15,0.2));
        inventoryItems.add(new ElectronicsProduct("P003","TV",1299.99,3,15.0));

        // Food items (expirable, shippable)
        inventoryItems.add(new PerishableProduct("P004","Cheese",12.99,20,LocalDate.now().plusDays(30),0.5));
        inventoryItems.add(new PerishableProduct("P005","Milk",3.99,50,LocalDate.now().plusDays(7),1.0));

        // Snack items (expirable, non-shippable)
        inventoryItems.add(new NonShippablePerishableProduct("P006","Biscuits",4.99,30,LocalDate.now().plusDays(90)));
        inventoryItems.add(new NonShippablePerishableProduct("P007","Chips",2.99,25,LocalDate.now().plusDays(60)));

        // Digital items (non-expirable, non-shippable)
        inventoryItems.add(new DigitalProduct("P008","Mobile Scratch Card",10.00,100));
        inventoryItems.add(new DigitalProduct("P009","Gift Card",25.00,50));
    }
    
    public void showAvailableItems(){
        System.out.println("Available Products :");
        for(Product p : inventoryItems){
            p.showProductDetails();
        }
    }

    @Override
    public Product findItemById (String itemId){
        for(Product p : inventoryItems){
            if(p.getProductIdentifier().equals(itemId)){
                return p;
            }
        }
        return null;
    }
    
    // Check if product is available (in stock and not expired)
    @Override
    public boolean isItemAvailable(String itemId, int requestedAmount) {
        Product item = findItemById(itemId);
        if (item == null) {
            return false;
        }
        
        // Check if enough quantity is available
        if (item.getAvailableStock() < requestedAmount) {
            return false;
        }
        
        // Check if product is expired (for perishable products)
        if (item.hasExpirationDate()) {
            if (item instanceof PerishableProduct) {
                PerishableProduct perishable = (PerishableProduct) item;
                if (perishable.isPastExpirationDate()) {
                    return false;
                }
            } else if (item instanceof NonShippablePerishableProduct) {
                NonShippablePerishableProduct perishable = (NonShippablePerishableProduct) item;
                if (perishable.isPastExpirationDate()) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // Update product quantity after purchase
    @Override
    public void updateItemStock(String itemId, int soldAmount) {
        Product item = findItemById(itemId);
        if (item != null) {
            item.setAvailableStock(item.getAvailableStock() - soldAmount);
        }
    }
}
