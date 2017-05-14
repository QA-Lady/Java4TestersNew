package prg.training.addressbook.base;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import prg.training.addressbook.utils.NavigationHelper;
import prg.training.addressbook.utils.appManager.AppManager;
import prg.training.addressbook.utils.appManager.WebDriverProvider;

/**
 * Created by QA Lady on 3/26/2017.
 */
public abstract class TestBase {

    protected static final ThreadLocal<AppManager> threadLocalAppManager = new ThreadLocal<AppManager>();
    protected static ThreadLocal<String> threadLocalBrowser = new ThreadLocal<>();
    public static WebDriverWait wait;

    public static WebDriver getDriver() {
        return WebDriverProvider.getDriver(threadLocalBrowser.get(), false);
    }

    public AppManager appManager() {
        return threadLocalAppManager.get();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional String browser) throws Exception {

        if (StringUtils.isEmpty(browser)) {
            browser = WebDriverProvider.DRIVER_DEFAULT;
        }
        String currentUrl = null;
        String testBrowser = threadLocalBrowser.get();
        if (testBrowser == null || !testBrowser.equals(browser)) {
            // Browser is not set yet or changed for test
            threadLocalBrowser.set(browser);
            WebDriver driver = WebDriverProvider.getDriver(browser, true);

            try {
                currentUrl = driver.getCurrentUrl();
            } catch (Exception ignored) {
                // Driver is not responding
            }
        }
        if (currentUrl == null || !currentUrl.startsWith(NavigationHelper.URL_HOME)) {
            getDriver().manage().window().maximize();
            getDriver().get(NavigationHelper.URL_HOME);
            //
            AppManager appManager = new AppManager();
            threadLocalAppManager.set(appManager);
            appManager().init();
            appManager().getLoginHelper().loginIfNotLoggedIn("admin", "secret");
            appManager().goTo().homePage(true);
        }

        //assigning wait values to be used in explicit waits
        wait = new WebDriverWait(getDriver(), 10);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        WebDriverProvider.quitDriver();
    }


}
