package prg.training.addressbook.base;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.File;

/**
 * Created by QA Lady on 3/26/2017.
 */
public class TestBase {

    public static WebDriver driver;
    DesiredCapabilities capabilities = new DesiredCapabilities();

    @BeforeSuite
    public void setUp() throws Exception {
        FirefoxProfile firefoxProfile = new FirefoxProfile(/*new File("C:/WORK/Training/Profiles/Firefox/")*/);
        firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
        driver = new FirefoxDriver(new FirefoxBinary(new File("C:/Dev_Tools/Drivers/ESR/firefox.exe")), firefoxProfile);
        //
        driver.manage().window().maximize();
        driver.get("http://localhost/addressbook/");
    }


    @AfterSuite
    public void tearDown() {
        driver.quit();
    }

    @BeforeTest
    public void doLogin() {
        loginIfNotLoggedIn("admin", "secret");
    }

    public void loginIfNotLoggedIn(String username, String password) {
        WebElement logoutElement = null;
        try {
            logoutElement = driver.findElement(By.xpath("//form/a[text()='Logout']"));
            return;
        } catch (Exception e) {
            enterText(By.name("user"), username);
            enterText(By.name("pass"), password);
            clickOn(By.xpath("//input[@value='Login']"));
        }

    }

    //common methods
    public void clickOn(By locator) {
        WebElement element = driver.findElement(locator);
        element.click();
    }

    public void enterText(By locator, String text) {
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    public void submit() {
        clickOn(By.name("submit"));
    }

    public static boolean isAlertPresent(FirefoxDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
