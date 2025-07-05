import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems;
    private InventoryService inventoryService;

    // Constructor without inventory service parameter
    public Cart() {
        this.cartItems = new ArrayList<>();
        this.inventoryService = null; // Will be set later
    }

    // Constructor with inventory service parameter 
    public Cart(InventoryService inventoryService) {
        this.cartItems = new ArrayList<>();
        this.inventoryService = inventoryService;
    }

    // Method to set inventory service reference
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    //Reasons why i choose Private implementation of CartItem :
    //Can't access internal fields directly
    //Prevents breaking encapsulation
    private static class CartItemImpl implements CartItem {
        private Product product;
        private int quantity;

        public CartItemImpl(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        @Override
        public Product getProduct() {
            return product;
        }

        @Override
        public int getQuantity() {
            return quantity;
        }

        @Override
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public double getSubtotal() {
            return product.getProductCost() * quantity;
        }
    }

    // Add product to cart with quantity validation
    public boolean addProduct(Product product, int quantity) {
        // Check if inventory service is available for validation
        if (inventoryService != null) {
            // Check if product is available in inventory
            if (!inventoryService.isItemAvailable(product.getProductIdentifier(), quantity)) {
                System.out.println("Error: Product " + product.getProductName() + " is not available in requested quantity or is expired/out of stock!");
                return false;
            }

            // Check if the product is already in the cart
            for (CartItem item : cartItems) {
                if (item.getProduct().getProductIdentifier().equals(product.getProductIdentifier())) {
                    // Check if total quantity (existing + new) doesn't exceed available stock
                    int totalQuantity = item.getQuantity() + quantity;
                    if (!inventoryService.isItemAvailable(product.getProductIdentifier(), totalQuantity)) {
                        System.out.println("Error: Cannot add " + quantity + " more " + product.getProductName() + ". Insufficient stock!");
                        return false;
                    }
                    item.setQuantity(totalQuantity);
                    System.out.println(quantity + " " + product.getProductName() + "(s) added to your cart. New quantity is " + item.getQuantity());
                    return true;
                }
            }
        }

        // If product is not in the cart, add it with the specified quantity
        cartItems.add(new CartItemImpl(product, quantity));
        System.out.println(quantity + " " + product.getProductName() + "(s) added to your cart.");
        return true;
    }

    // Remove product from cart with quantity
    public void removeProduct(String productId, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getProductIdentifier().equals(productId)) {
                if (item.getQuantity() > quantity) {
                    item.setQuantity(item.getQuantity() - quantity);
                    System.out.println(quantity + " " + item.getProduct().getProductName() + "(s) removed from your cart. Remaining quantity is " + item.getQuantity());
                } else {
                    cartItems.remove(item);
                    System.out.println(item.getProduct().getProductName() + " removed from your cart!");
                }
                break;
            }
        }
    }

    // Display all products in the cart
    public void displayCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }
        
        System.out.println("Cart Items:");
        for (CartItem item : cartItems) {
            System.out.println("- " + item.getProduct().getProductName() + " x" + item.getQuantity() + " @ $" + item.getProduct().getProductCost() + " = $" + item.getSubtotal());
        }
        System.out.println("Cart Subtotal: $" + calculateSubtotal());
    }

    // Calculate subtotal (sum of all items' prices)
    public double calculateSubtotal() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    // Calculate shipping fees
    public double calculateShippingFees() {
        double totalWeight = 0;
        for (CartItem item : cartItems) {
            if (item.getProduct().needsPhysicalDelivery()) {
                totalWeight += item.getProduct().getShippingWeight() * item.getQuantity();
            }
        }
        
        // Simple shipping calculation: $5 base + $2 per kg
        if (totalWeight > 0) {
            return 5.0 + (totalWeight * 2.0);
        }
        return 0.0; // No shipping for digital items
    }

    // Calculate total cost (subtotal + shipping)
    public double calculateTotalCost() {
        return calculateSubtotal() + calculateShippingFees();
    }

    // Check if cart is empty
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    // Clear cart after successful checkout
    public void clearCart() {
        cartItems.clear();
    }

    // Public methods for CheckoutService to access cart items without exposing implementation
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        for (CartItem item : cartItems) {
            products.add(item.getProduct());
        }
        return products;
    }

    public List<Integer> getQuantities() {
        List<Integer> quantities = new ArrayList<>();
        for (CartItem item : cartItems) {
            quantities.add(item.getQuantity());
        }
        return quantities;
    }

    public int getItemCount() {
        return cartItems.size();
    }

    public Product getProductAt(int index) {
        if (index >= 0 && index < cartItems.size()) {
            return cartItems.get(index).getProduct();
        }
        return null;
    }

    public int getQuantityAt(int index) {
        if (index >= 0 && index < cartItems.size()) {
            return cartItems.get(index).getQuantity();
        }
        return 0;
    }
}
