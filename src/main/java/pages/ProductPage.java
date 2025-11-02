package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;

import java.util.List;
import java.util.ArrayList;

public class ProductPage extends BasePage {

    @FindBy(css = "[data-test='title']")
    private WebElement pageTitle;

    @FindBy(css = ".inventory_item")
    private List<WebElement> products;

    @FindBy(css = "[data-test='shopping-cart-link']")
    private WebElement cartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public boolean isOnProductsPage() {
        return isElementDisplayed(pageTitle) && "Products".equals(getTitle());
    }

    public ProductInfo getProductInfo(int index) {
        if (index >= products.size()) {
            throw new IndexOutOfBoundsException("Product index " + index + " is out of bounds. Total products: " + products.size());
        }

        WebElement product = products.get(index);
        String name = product.findElement(By.cssSelector(".inventory_item_name")).getText();
        String priceText = product.findElement(By.cssSelector(".inventory_item_price")).getText();
        String description = product.findElement(By.cssSelector(".inventory_item_desc")).getText();
        double price = Double.parseDouble(priceText.replace("$", ""));

        return new ProductInfo(product, name, price, priceText, description);
    }


    public ProductInfo findProductWithHighestPrice() {
        waitForElement(products.getFirst());
        List<ProductInfo> productsInfo = getAllProductsInfo();
        productsInfo.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
        if (productsInfo.isEmpty()) {
            throw new RuntimeException("No products found on the page");
        }
        return productsInfo.getFirst();
    }

    public void addProductToCart(WebElement product) {
        WebElement addToCartButton = product.findElement(By.cssSelector("[data-test^='add-to-cart-']"));
        String productName = product.findElement(By.cssSelector(".inventory_item_name")).getText();

        System.out.println("Adding to cart: " + productName);
        clickElement(addToCartButton);
        System.out.println("Added product to cart: " + productName);
        try {
            WebElement removeButton = product.findElement(By.cssSelector("[data-test^='remove-']"));
            if (removeButton.isDisplayed()) {
                System.out.println("Verified: Product successfully added (Remove button now visible)");
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not verify if product was added to cart");
        }
    }

    public List<ProductInfo> getAllProductsInfo() {
        List<ProductInfo> productsInfo = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            productsInfo.add(getProductInfo(i));
        }

        return productsInfo;
    }

    public static class ProductInfo {
        private final WebElement element;
        private final String name;
        private final double price;
        private final String priceText;
        private final String description;

        public ProductInfo(WebElement element, String name, double price, String priceText, String description) {
            this.element = element;
            this.name = name;
            this.price = price;
            this.priceText = priceText;
            this.description = description;
        }

        public WebElement getElement() {
            return element;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getPriceText() {
            return priceText;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return String.format("ProductInfo{name='%s', price=%.2f, description='%s'}",
                    name, price, description);
        }
    }
}