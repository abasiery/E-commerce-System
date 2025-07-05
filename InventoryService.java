public interface InventoryService {
    boolean isItemAvailable(String itemId, int quantity);
    void updateItemStock(String itemId, int quantity);
    Product findItemById(String itemId);
} 