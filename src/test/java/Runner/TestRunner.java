package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Feature/highest-priced-product.feature",
        glue = {"StepDefinition","SetUp"},
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class TestRunner {
}
