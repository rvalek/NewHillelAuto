package hillelauto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import hillelauto.reporting.TestRail;

public class WebDriverTestBase {
    protected static WebDriver browser;
    private TestRail trReport;

    static {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    }

    @BeforeTest(alwaysRun = true)
    public static void setUp() {
        browser = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized", "--incognito"));
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebDriverTools.setDriver(browser);
    }

    @AfterTest(alwaysRun = true)
    public static void finish() {
        browser.close();
    }

    @BeforeClass(groups = "TestrailReport")
    protected void prepareTestRailRun() throws Exception {
        String baseURL = "https://hillelrob.testrail.io/";
        System.out.println("Reporting to " + baseURL);

        trReport = new TestRail(baseURL);
        trReport.setCreds("rvalek@intersog.com", "hillel");
        trReport.startRun(1, "Jira Auto - " + new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date()));
    }

    @AfterMethod(groups = "TestrailReport")
    protected void reportResult(ITestResult testResult) throws Exception {
        String testDescription = testResult.getMethod().getDescription();
        trReport.setResult(Integer.parseInt(testDescription.substring(0, testDescription.indexOf("."))),
                testResult.getStatus());
    }

    @AfterClass(groups = "TestrailReport")
    protected void closeTestRailRun() throws Exception {
        trReport.endRun();
    }
}