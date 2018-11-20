package hillelauto;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import hillelauto.reporting.TestRail;

public class WebDriverTestBase {
    protected WebDriver browser;
    private TestRail trReport;

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

    @Parameters({ "testRailProjectId", "testRailRunPrefix" })
    @BeforeTest(groups = "TestRailReport")
    public void prepareTestRailRun(String projectId, String runPrefix) throws Exception {
        String baseURL = "https://hillelrob.testrail.io/";
        System.out.println("Reporting to " + baseURL);

        trReport = new TestRail(baseURL);
        trReport.setCreds("rvalek@intersog.com", "hillel");
        trReport.startRun(Integer.parseInt(projectId), runPrefix + " Robert Auto - " + Helper.timeStamp());
    }

    @AfterMethod(groups = "TestRailReport")
    protected void reportResult(ITestResult testResult) throws Exception {
        String testDescription = testResult.getMethod().getDescription();
        try {
            int caseId = Integer.parseInt(testDescription.substring(0, testDescription.indexOf(".")));
            trReport.setResult(caseId, testResult.getStatus());
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            System.out.println(testDescription + " - Case ID missing; not reporting to TestRail.");
        }

    }

    @AfterTest(groups = "TestRailReport")
    public void closeTestRailRun() throws Exception {
        trReport.endRun();
    }
}