package SetUp;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.ConfigManager;
import utils.WebDriverManager;

public class GlobalSetup {
    @Before
    public void setUp(Scenario scenario) {
        WebDriverManager.initializeDriver();
        System.out.println("WebDriver initialized successfully");
    }

    @After
    public void tearDown(Scenario scenario) {
        // Take screenshot on failure if configured
        if (scenario.isFailed() && ConfigManager.isScreenshotOnFailure()) {
            takeScreenshot(scenario);
        }

        // Print scenario result
        System.out.println("Scenario '" + scenario.getName() + "' " +
                (scenario.isFailed() ? "FAILED" : "PASSED"));

        // Quit WebDriver
        WebDriverManager.quitDriver();

        System.out.println("Test cleanup completed");
    }

    /**
     * Take screenshot and attach to scenario
     * @param scenario Current scenario
     */
    private void takeScreenshot(Scenario scenario) {
        try {
            if (WebDriverManager.isDriverInitialized()) {
                byte[] screenshot = ((TakesScreenshot) WebDriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot - " + scenario.getName());
                System.out.println("Screenshot attached for failed scenario: " + scenario.getName());
            }
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
}
