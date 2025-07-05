import java.util.List;

// Interface for shippable items
interface ShippableItem {
    String getName();
    double getWeight();
}

// Shipping Service implementation
public class ShippingService {
    
    public void shipItems(List<ShippableItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items to ship.");
            return;
        }
        
        System.out.println("=== Shipping Details ===");
        double totalWeight = 0;
        
        for (ShippableItem item : items) {
            System.out.println("Shipping: " + item.getName() + " (Weight: " + item.getWeight() + " kg)");
            totalWeight += item.getWeight();
        }
        
        System.out.println("Total items to ship: " + items.size());
        System.out.println("Total weight: " + totalWeight + " kg");
        System.out.println("Items have been sent to shipping department.");
    }
} 