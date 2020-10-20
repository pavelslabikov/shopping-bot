package app;

import storages.IStorageItem;

public class Customer {
    private final Cart cart;
    private final int id;
    private IStorageItem itemToAdd;
    private state state;

    public Customer(int id) {
        cart = new Cart();
        this.id = id;
        state = app.state.start;
    }

    public int getId() {
        return id;
    }

    public state getState() { return state; }

    public void setState(state state) { this.state = state; }

    public IStorageItem getItemToAdd() { return itemToAdd; }

    public void setItemToAdd(IStorageItem itemToAdd) { this.itemToAdd = itemToAdd; }

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
