package StepDefinition;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.LoginPage;
import pages.ProductPage;
import utils.WebDriverManager;

import static utils.ConfigManager.getBaseUrl;

public class LoginSteps {

    private final LoginPage loginPage;
    private final ProductPage productPage;

    public LoginSteps() {
        this.loginPage = new LoginPage(WebDriverManager.getDriver());
        this.productPage = new ProductPage(WebDriverManager.getDriver());
    }

    @Given("I am on the SauceDemo login page")
    public void i_am_on_the_sauce_demo_login_page() {
        loginPage.navigateTo(getBaseUrl());
        Assert.assertTrue("Should be on login page", loginPage.isOnLoginPage());
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        loginPage.login(username, password);
    }

    @Then("I should be successfully logged in")
    public void i_should_be_successfully_logged_in() {
        String currentUrl = WebDriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue("Should be redirected to inventory page after successful login. Current URL: " + currentUrl,
                currentUrl != null && currentUrl.contains("inventory.html"));
    }

    @Then("I should see the Products page")
    public void i_should_see_the_products_page() {
        Assert.assertTrue("Should be on Products page", productPage.isOnProductsPage());
        Assert.assertEquals("Page title should be 'Products'", "Products", productPage.getTitle());
    }
}