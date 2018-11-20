package hillelauto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Helper {
    private WebDriver browser;

    public Helper(WebDriver currentBrowser) {
        browser = currentBrowser;
    }

    public WebElement findAndFill(By selector, String value) {
        WebElement elem = browser.findElement(selector);

        elem.sendKeys(value);
        return elem;
    }

    public static String timeStamp() {
        return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
    }
}