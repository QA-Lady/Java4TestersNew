package prg.training.addressbook.base;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import prg.training.addressbook.utils.NavigationHelper;
import prg.training.addressbook.utils.appManager.AppManager;
import prg.training.addressbook.utils.appManager.WebDriverProvider;

/**
 * Created by QA Lady on 3/26/2017.
 */
public class TestBase {

    protected final AppManager appManager = new AppManager();
    private static ThreadLocal<String> threadLocalBrowser = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return WebDriverProvider.getDriver(threadLocalBrowser.get());
    }

    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional String browser) throws Exception {
        if (StringUtils.isEmpty(browser)) {
            browser = WebDriverProvider.DRIVER_DEFAULT;
        }
        String testBrowser = threadLocalBrowser.get();
        if (testBrowser == null || !testBrowser.equals(browser)) {
            // Browser is not set yet or changed for test
            threadLocalBrowser.set(browser);
            WebDriver driver = WebDriverProvider.getDriver(browser);
            String currentUrl = null;
            try {
                currentUrl = driver.getCurrentUrl();
            } catch (Exception ignored) {
                // Driver is not responding
            }
            if (currentUrl == null || !currentUrl.startsWith(NavigationHelper.URL_HOME)) {
                driver.manage().window().maximize();
                driver.get(NavigationHelper.URL_HOME);
                //
                appManager.init();
                appManager.getLoginHelper().loginIfNotLoggedIn("admin", "secret");
                appManager.getNavigationHelper().goToHomePage(true);
            }
        }
    }

    @AfterSuite
    public void tearDown() {
        WebDriverProvider.quitDriver();
    }


}
