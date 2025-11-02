package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(css = "[data-test='username']")
    private WebElement usernameField;

    @FindBy(css = "[data-test='password']")
    private WebElement passwordField;

    @FindBy(css = "[data-test='login-button']")
    private WebElement loginButton;

    @FindBy(css = ".login_logo")
    private WebElement loginLogo;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void clickLoginButton() {
        clickElement(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isOnLoginPage() {
        return isElementDisplayed(loginLogo);
    }
}