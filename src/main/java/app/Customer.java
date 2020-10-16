package app;

public class Customer {
    private final Cart cart;
    private final int id;

    public Customer(int id) {
        cart = new Cart();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        var customer = (Customer) o;
        return id == customer.id;
    }
}
