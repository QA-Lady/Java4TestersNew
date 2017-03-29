package prg.training.addressbook.utils.appManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class WebDriverProvider {

    private static WebDriver driver;
    private DesiredCapabilities capabilities = new DesiredCapabilities();

    public static WebDriver getDriver() {
        return driver;
    }

    public static void configDriver() {
        FirefoxProfile firefoxProfile = new FirefoxProfile(/*new File("C:/WORK/Training/Profiles/Firefox/")*/);
        firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
        driver = new FirefoxDriver(new FirefoxBinary(new File("C:/Dev_Tools/Drivers/ESR/firefox.exe")), firefoxProfile);
    }
}
