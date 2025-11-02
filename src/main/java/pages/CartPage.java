package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

import java.util.List;
import java.util.ArrayList;

/**
 * Cart Page class containing elements and methods for cart functionality
 */
public class CartPage extends BasePage {

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "[data-test='continue-shopping']")
    private WebElement continueShoppingButton;

    @FindBy(css = "[data-test='shopping-cart-link']")
    private WebElement cartLink;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToCart() {
        clickElement(cartLink);
        waitForPageLoad();
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public boolean isOnCartPage() {
        return isElementDisplayed(pageTitle) && "Your Cart".equals(getTitle());
    }

    public CartItem getCartItem(int index) {
        if (index >= cartItems.size()) {
            throw new IndexOutOfBoundsException("Cart item index " + index + " is out of bounds. Total items: " + cartItems.size());
        }

        WebElement item = cartItems.get(index);
        String name = item.findElement(By.cssSelector(".inventory_item_name")).getText();
        String description = item.findElement(By.cssSelector(".inventory_item_desc")).getText();
        String priceText = item.findElement(By.cssSelector(".inventory_item_price")).getText();
        String quantity = item.findElement(By.cssSelector(".cart_quantity")).getText();

        double price = Double.parseDouble(priceText.replace("$", ""));

        return new CartItem(item, name, description, price, priceText, Integer.parseInt(quantity));
    }

    public List<CartItem> getAllCartItems() {
        List<CartItem> items = new ArrayList<>();

        for (int i = 0; i < cartItems.size(); i++) {
            items.add(getCartItem(i));
        }

        return items;
    }


    public static class CartItem {
        private final WebElement element;
        private final String name;
        private final String description;
        private final double price;
        private final String priceText;
        private final int quantity;

        public CartItem(WebElement element, String name, String description, double price, String priceText, int quantity) {
            this.element = element;
            this.name = name;
            this.description = description;
            this.price = price;
            this.priceText = priceText;
            this.quantity = quantity;
        }

        public WebElement getElement() {
            return element;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public double getPrice() {
            return price;
        }

        public String getPriceText() {
            return priceText;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotalPrice() {
            return price * quantity;
        }

        @Override
        public String toString() {
            return String.format("CartItem{name='%s', price=%.2f, quantity=%d, total=%.2f}",
                    name, price, quantity, getTotalPrice());
        }
    }
}