package prg.training.addressbook.utils.appManager;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Optional;
import prg.training.addressbook.base.TestBase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class WebDriverProvider {

    public static final String DRIVER_DEFAULT = BrowserType.FIREFOX;

    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();//EventFiringWebDriver can also be added here it implements WebDriver
    private static List<WebDriver> drivers = new ArrayList<>();
    private DesiredCapabilities capabilities = new DesiredCapabilities();

    private static WebDriver createAndConfigDriver(@Optional String browser) {
        WebDriver driver = null;
        if (StringUtils.isEmpty(browser) || BrowserType.FIREFOX.equals(browser)) {
            FirefoxProfile firefoxProfile = new FirefoxProfile(/*new File("C:/WORK/Training/Profiles/Firefox/")*/);
            firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
            driver = new FirefoxDriver(new FirefoxBinary(new File("C:/Dev_Tools/Drivers/ESR/firefox.exe")), firefoxProfile);
        } else if (BrowserType.CHROME.equalsIgnoreCase(browser)) {
            driver = new ChromeDriver();
        } else if (BrowserType.IE.equalsIgnoreCase(browser)) {
            driver = new InternetExplorerDriver();
        } else if (StringUtils.equals(TestBase.target, "remote") && StringUtils.isNotEmpty(TestBase.properties.getProperty("selenium.server"))) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win7")));
            try {
                driver = new RemoteWebDriver(new URL(TestBase.properties.getProperty("selenium.server")), capabilities);
            } catch (MalformedURLException e) {
                System.out.println("URL provided is not correct");
                e.printStackTrace();
            }
        } else {
            System.out.println("Wrong value was provided for argument browser '" + browser + "'");
            System.out.println("Will default to FireFox driver with new profile");
            driver = new FirefoxDriver(new FirefoxBinary(new File("C:/Dev_Tools/Drivers/ESR/firefox.exe")), new FirefoxProfile());
        }
        //set thread local driver
        threadLocalDriver.set(driver);
        //for later use to close all drivers in all threads
        drivers.add(driver);
        return driver;
    }

    public static WebDriver getDriver(@Optional String browser, boolean forceCreate) {
        WebDriver driver = threadLocalDriver.get();
        if (driver == null || forceCreate) {
            driver = createAndConfigDriver(browser);
        }
        return driver;
    }

    public static void quitDriver() {
        for (WebDriver webDriver : drivers) {
            webDriver.close();
            webDriver.quit();
        }
    }

}
