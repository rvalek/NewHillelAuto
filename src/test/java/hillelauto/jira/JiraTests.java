package hillelauto.jira;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import hillelauto.WebDriverTestBase;
import hillelauto.reporting.TestRail;

public class JiraTests extends WebDriverTestBase {
    private LoginPage loginPage;
    private IssuePage issuePage;

    private TestRail trReport;

    @BeforeClass(alwaysRun = true)
    public void initPages() {
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        issuePage = PageFactory.initElements(browser, IssuePage.class);
        System.out.println("Jira Pages Initialized");
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

    @Test(description = "1. Invalid Login", priority = -1)
    public void failureLogin() {
        loginPage.failureLogin();
    }

    @Test(description = "2. Valid Login", groups = { "Sanity" })
    public void successfulLogin() {
        loginPage.successfulLogin();
    }

    @Test(description = "3. Create issue", dependsOnMethods = { "successfulLogin" }, groups = { "Sanity", "Issues" })
    public void createIssue() throws InterruptedException {
        issuePage.createIssue();
    }

    @Test(description = "4. Open issue", dependsOnMethods = { "createIssue" }, groups = { "Sanity", "Issues" })
    public void openIssue() {
        issuePage.openIssue();
    }

    @Test(description = "5. Uplaod Attachment", dependsOnMethods = { "openIssue" }, groups = { "Issues.Attachments" })
    public void uploadAttachment() {
        issuePage.uploadAttachment();
    }

    @Test(description = "Download Attachment", dependsOnMethods = { "uploadAttachment" }, groups = {
            "Issues.Attachments", "disabled" })
    public void downloadAttachment() {
        // loginPage.downloadAttachment();
    }
}
