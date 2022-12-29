import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.List;

public class MyClassJUnit {
    WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        options.addPreference("toolkit.telemetry.archive.enabled", false);
        options.addPreference("toolkit.telemetry.cachedClientID", "{}");
        options.addPreference("toolkit.telemetry.enabled", false);
        options.addPreference("toolkit.telemetry.infoURL", "{}");
        options.addPreference("toolkit.telemetry.previousBuildID", "{}");
        options.addPreference("toolkit.telemetry.server", "{}");
        options.addPreference("toolkit.telemetry.server_owner", "{}");
        options.addPreference("toolkit.telemetry.unified", false);
        options.addPreference("fission.webContentIsolationStrategy", 0);
        options.addPreference("fission.bfcacheInParent", false);
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void getList() {
        List<String> searchKeywords = Utils.ReadExcel();
        int iteration = 0;
        for (String searchKeyword : searchKeywords) {
            List<String> searchResults = Utils.getSearchResults(driver, searchKeyword);
            List<String> validSearchResults = Utils.GetLargestAndSmallestString(searchResults);
            Utils.WriteExcel(iteration, validSearchResults);
            iteration++;
        }
    }

    @After
    public void quit() {
        driver.quit();
    }
}