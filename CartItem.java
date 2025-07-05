public interface CartItem {
    Product getProduct();
    int getQuantity();
    void setQuantity(int quantity);
    double getSubtotal();
} 