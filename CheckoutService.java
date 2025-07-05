import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private InventoryService inventoryService;
    private ShippingService shippingService;
    
    public CheckoutService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.shippingService = new ShippingService();
    }
    
    public boolean processCheckout(User user) {
        Cart cart = user.getCart();
        
        // Check if cart is empty
        if (cart.isEmpty()) {
            System.out.println("Error: Cart is empty!");
            return false;
        }
        
        // Validate all items in cart
        if (!validateCartItems(cart)) {
            return false;
        }
        
        // Calculate costs
        double subtotal = cart.calculateSubtotal();
        double shippingFees = cart.calculateShippingFees();
        double totalAmount = cart.calculateTotalCost();
        
        // Check if user has sufficient balance
        if (user.getBalance() < totalAmount) {
            System.out.println("Error: Insufficient balance!");
            System.out.println("Required: $" + totalAmount);
            System.out.println("Available: $" + user.getBalance());
            return false;
        }
        
        // Process the checkout
        System.out.println("=== Checkout Details ===");
        System.out.println("Order Subtotal: $" + subtotal);
        System.out.println("Shipping Fees: $" + shippingFees);
        System.out.println("Total Amount: $" + totalAmount);
        System.out.println("Customer Balance Before: $" + user.getBalance());
        
        // Deduct amount from user balance
        user.deductBalance(totalAmount);
        
        System.out.println("Customer Balance After: $" + user.getBalance());
        
        // Update shop inventory
        updateShopInventory(cart);
        
        // Send shippable items to shipping service
        sendItemsToShipping(cart);
        
        // Clear the cart
        cart.clearCart();
        
        System.out.println("Thank you for shopping!");
        return true;
    }
    
    private boolean validateCartItems(Cart cart) {
        for (int i = 0; i < cart.getItemCount(); i++) {
            Product product = cart.getProductAt(i);
            int requestedQuantity = cart.getQuantityAt(i);
            
            // Check if product is still available
            if (!inventoryService.isItemAvailable(product.getProductIdentifier(), requestedQuantity)) {
                System.out.println("Error: Product " + product.getProductName() + " is no longer available in requested quantity or is expired!");
                return false;
            }
        }
        return true;
    }
    
    private void updateShopInventory(Cart cart) {
        for (int i = 0; i < cart.getItemCount(); i++) {
            Product product = cart.getProductAt(i);
            int quantity = cart.getQuantityAt(i);
            inventoryService.updateItemStock(product.getProductIdentifier(), quantity);
        }
    }
    
    private void sendItemsToShipping(Cart cart) {
        List<ShippableItem> shippableItems = new ArrayList<>();
        
        for (int i = 0; i < cart.getItemCount(); i++) {
            Product product = cart.getProductAt(i);
            int quantity = cart.getQuantityAt(i);
            
            if (product.needsPhysicalDelivery()) {
                // Create shippable item for each quantity
                for (int j = 0; j < quantity; j++) {
                    shippableItems.add(new ShippableItem() {
                        @Override
                        public String getName() {
                            return product.getProductName();
                        }
                        
                        @Override
                        public double getWeight() {
                            return product.getShippingWeight();
                        }
                    });
                }
            }
        }
        
        shippingService.shipItems(shippableItems);
    }
} 