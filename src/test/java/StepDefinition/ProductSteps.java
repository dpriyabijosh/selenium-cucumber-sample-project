package StepDefinition;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.ProductPage;
import pages.ProductPage.ProductInfo;
import pages.CartPage;
import utils.WebDriverManager;

import java.util.List;
import java.util.ArrayList;

public class ProductSteps {

    private final ProductPage productPage;
    private final CartPage cartPage;
    private ProductInfo highestPricedProduct;

    public ProductSteps() {
        this.productPage = new ProductPage(WebDriverManager.getDriver());
        this.cartPage = new CartPage(WebDriverManager.getDriver());
    }

    @When("I find the product with highest price")
    public void i_find_the_product_with_highest_price() {
        highestPricedProduct = productPage.findProductWithHighestPrice();
        Assert.assertNotNull("Should find a product with highest price", highestPricedProduct);
    }

    @When("I add the highest priced product to cart")
    public void i_add_the_highest_priced_product_to_cart() {
        if (highestPricedProduct == null) {
            highestPricedProduct = productPage.findProductWithHighestPrice();
        }
        productPage.addProductToCart(highestPricedProduct.getElement());
    }

    @Then("the highest priced product should be added successfully")
    public void the_highest_priced_product_should_be_added_successfully() {
        Assert.assertNotNull("Highest priced product should be found", highestPricedProduct);
        cartPage.navigateToCart();
        Assert.assertTrue("Should be on Cart page", cartPage.isOnCartPage());
        List<CartPage.CartItem> cartItems = cartPage.getAllCartItems();
        List<String> cartItemNames = new ArrayList<>();
        for (CartPage.CartItem item : cartItems) {
            cartItemNames.add(item.getName());
        }

        Assert.assertFalse("Cart should not be empty", cartItemNames.isEmpty());
        Assert.assertTrue("Highest priced product '" + highestPricedProduct.getName() + "' should be in cart",
                cartItemNames.contains(highestPricedProduct.getName()));
    }
}