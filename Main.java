import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        Shop retailStore = new Shop();
        User customer = new User("Abdelrahman", 5000.0); // Customer with $5000 balance
        customer.getCart().setInventoryService(retailStore); // Set inventory service in cart
        CheckoutService paymentProcessor = new CheckoutService(retailStore);
        boolean continueShopping = true;

        System.out.println("=== Retail Management System ===");
        System.out.println("Welcome " + customer.getName() + "! Your balance: $" + customer.getBalance());
        System.out.println();

        while (continueShopping) {
            retailStore.showAvailableItems();
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. Add Item to Cart");
            System.out.println("2. Remove Item from Cart");
            System.out.println("3. View Shopping Cart");
            System.out.println("4. Complete Purchase");
            System.out.println("5. Exit System");
            System.out.print("Please select an option: ");
            
            int userChoice = inputScanner.nextInt();
            inputScanner.nextLine(); // Consume newline
            
            switch (userChoice) {
                case 1:
                    System.out.print("Enter Product ID to add to cart: ");
                    String selectedItemId = inputScanner.nextLine();
                    Product selectedItem = retailStore.findItemById(selectedItemId);
                    if (selectedItem != null) {
                        System.out.print("Enter quantity to add: ");
                        int itemQuantity = inputScanner.nextInt();
                        inputScanner.nextLine(); 
                        customer.getCart().addProduct(selectedItem, itemQuantity);
                    } else {
                        System.out.println("Product not found!");
                    }
                    break;

                case 2:
                    System.out.print("Enter Product ID to remove from cart: ");
                    String removeItemId = inputScanner.nextLine();
                    System.out.print("Enter quantity to remove: ");
                    int removeQuantity = inputScanner.nextInt();
                    inputScanner.nextLine(); 
                    customer.getCart().removeProduct(removeItemId, removeQuantity);
                    break;
                    
                case 3:
                    customer.getCart().displayCart();
                    break;
                    
                case 4:
                    boolean purchaseSuccess = paymentProcessor.processCheckout(customer);
                    if (purchaseSuccess) {
                        System.out.println("Purchase completed successfully!");
                    } else {
                        System.out.println("Purchase failed. Please try again.");
                    }
                    break;
                    
                case 5:
                    System.out.println("Thank you for using our Retail Management System!");
                    continueShopping = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println(); //Adding spaces between iterations
        }
        
        inputScanner.close();
    }
}