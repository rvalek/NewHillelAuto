package HillelAutomation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JiraTests {
	static WebDriver browser;

	static String baseURL = "http://jira.hillel.it:8080/";
	static String username = "autorob";
	static String password = "forautotests";

	static String newIssueSummary = "AutoTest " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	static String newIssuePath;

	@BeforeTest
	protected static void setUp() {
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized", "--incognito");
		browser = new ChromeDriver(options);
		browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		browser.get(baseURL);
	}

	@Test(description = "Valid Login")
	private void logIn() {
		clearAndFill(By.cssSelector("input#login-form-username"), username);
		clearAndFill(By.cssSelector("input#login-form-password"), password).submit();

		Assert.assertEquals(username,
				browser.findElement(By.cssSelector("a#header-details-user-fullname")).getAttribute("data-username"));
	}

	@Test(description = "Create issue", dependsOnMethods = { "logIn" })
	private void createTicket() throws InterruptedException {
		browser.findElement(By.cssSelector("a#create_link")).click();

		clearAndFill(By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");

		new FluentWait<WebDriver>(browser).withTimeout(5, TimeUnit.SECONDS).pollingEvery(500, TimeUnit.MILLISECONDS)
				.ignoring(InvalidElementStateException.class).until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return clearAndFill(By.cssSelector("input#summary"), newIssueSummary);
					}
				}).submit();

		// ((JavascriptExecutor) browser).executeScript("window.scrollBy(0,250)");

		List<WebElement> newIssueLinks = browser.findElements(By.cssSelector("a.issue-created-key"));

		Assert.assertTrue(newIssueLinks.size() != 0);

		newIssuePath = newIssueLinks.get(0).getAttribute("href");
	}

	@Test(description = "Open issue", dependsOnMethods = { "createTicket" }, groups = { "Sanity" })
	private void openTicket() {
		browser.get(newIssuePath);
		Assert.assertTrue(browser.getTitle().contains(newIssueSummary));
	}

	@Test(description = "Uplaod Attachment", dependsOnMethods = { "openTicket" }, groups = { "Attachments" })
	private void uplaodAttachment() throws InterruptedException {

	}

	@AfterTest
	private void finish() {
		browser.close();
	}

	public static WebElement clearAndFill(By selector, String data) {
		WebElement element = browser.findElement(selector);
		element.clear();
		element.sendKeys(data);

		return element;
	}
}
