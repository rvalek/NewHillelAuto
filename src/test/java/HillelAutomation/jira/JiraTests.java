package HillelAutomation.jira;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import HillelAutomation.WebDriverTestBase;

public class JiraTests extends WebDriverTestBase {
	private LoginPage loginPage;
	private IssuePage issuePage;

	@BeforeClass
	public void initPages() {
		loginPage = PageFactory.initElements(browser, LoginPage.class);
		issuePage = PageFactory.initElements(browser, IssuePage.class);
		System.out.println("Jira Pages Initialized");
	}

	@Test(description = "Invalid Login")
	public void failureLogin() {
		loginPage.failureLogin();
	}

	@Test(description = "Valid Login", priority = 1)
	public void successfulLogin() {
		loginPage.successfulLogin();
	}

	@Test(description = "Create issue", dependsOnMethods = { "logIn" })
	public void createIssue() throws InterruptedException {
		issuePage.createIssue();
	}

	@Test(description = "Open issue", dependsOnMethods = { "createTicket" }, groups = { "Sanity" })
	public void openIssue() {
		issuePage.openIssue();
	}

	@Test(description = "Uplaod Attachment", dependsOnMethods = { "openTicket" }, groups = { "Attachments" })
	public void uploadAttachment() {
		issuePage.uploadAttachment();
	}

	// @Test(description = "Download Attachment", dependsOnMethods = { "uploadAttachment" }, groups = { "Attachments" })
	// public void downloadAttachment() {
	// 	loginPage.downloadAttachment();
	// }
}
