public class User {
    private String name;
    private Cart cart;
    private double balance;

    public User(String name, double balance) {
        this.name = name;
        this.cart = new Cart();
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Cart getCart() {
        return cart;
    }

    public double getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }
}
