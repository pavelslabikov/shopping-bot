import models.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import storages.test.TestStorageItem;

public class TestCart {
    private Cart cart;

    @Before
    public void setUpCart() {
        cart = new Cart();
    }

    @Test
    public void testClearingEmptyCart() {
        Assert.assertTrue(cart.isEmpty());
        cart.removeItems();
        Assert.assertTrue(cart.isEmpty());
    }

    @Test
    public void testClearingNonEmptyCart() {
        var testItem = new TestStorageItem(1, 5);
        cart.addItem(testItem, 5);
        Assert.assertFalse(cart.isEmpty());
        cart.removeItems();
        Assert.assertTrue(cart.isEmpty());
    }

    @Test
    public void TestAddingSameItem() {
        var testItem = new TestStorageItem(1, 5);
        cart.addItem(testItem, 5);
        cart.addItem(testItem, 5);
        Assert.assertEquals(1, cart.getItems().size());
    }

    @Test
    public void testGettingItems() {
        var testItem = new TestStorageItem(1, 5);
        cart.addItem(testItem, 4);
        Assert.assertTrue(cart.getItems().contains(testItem));
    }
}
