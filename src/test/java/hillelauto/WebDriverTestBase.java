package hillelauto;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class WebDriverTestBase {
    protected WebDriver browser;

    private HashMap<Integer, Integer> testResults = new HashMap<>();

    static {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    }

    @BeforeTest(alwaysRun = true)
    public void setUp() {
        browser = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized", "--incognito"));
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterTest(alwaysRun = true)
    public void finish() {
        browser.close();
    }
}