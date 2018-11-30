package hillelauto.jira;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import hillelauto.Helper;

public class LoginPage {
    private final By inputUsername = By.cssSelector("input#login-form-username");
    private final By inputPassword = By.cssSelector("input#login-form-password");
    private final WebDriver browser;
    private final Helper h;
    @FindBy(css = "a#header-details-user-fullname")
    private WebElement buttonProfile;
    @FindBy(css = "div#usernameerror")
    private List<WebElement> messageError;

    public LoginPage(WebDriver browser) {
        this.browser = browser;
        this.h = new Helper(browser);
    }

    public boolean erroMessageIsShown() {
        return this.messageError.size() != 0;
    }

    public String getCurrentUserName() {
        return buttonProfile.getAttribute("data-username");
    }

    public void successfulLogin() {
        login(true);
    }

    public void failureLogin() {
        login(false);
    }

    private void login(boolean successful) {
        browser.get(JiraVars.baseURL);

        h.findAndFill(inputUsername, JiraVars.username);
        h.findAndFill(inputPassword, successful ? JiraVars.password : "badPassword").submit();
    }
}