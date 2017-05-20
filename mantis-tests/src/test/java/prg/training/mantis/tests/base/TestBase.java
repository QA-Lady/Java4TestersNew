package prg.training.mantis.tests.base;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import prg.training.mantis.utils.appmanager.AppManager;
import prg.training.mantis.utils.appmanager.WebDriverProvider;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by QA Lady on 5/20/2017.
 */
public abstract class TestBase {

    Logger log = LoggerFactory.getLogger(TestBase.class);

    protected static final ThreadLocal<AppManager> threadLocalAppManager = new ThreadLocal<AppManager>();
    protected static ThreadLocal<String> threadLocalBrowser = new ThreadLocal<>();
    public static WebDriverWait wait;
    public Properties properties = new Properties();
    public static String baseUrl;
    public static final String URL_HOME = "http://localhost/mantisbt-2.4.0/";

    public static WebDriver getDriver() {
        return WebDriverProvider.getDriver(threadLocalBrowser.get(), false);
    }

    public AppManager appManager() {
        return threadLocalAppManager.get();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional String browser) throws Exception {
        if (StringUtils.isNotEmpty(System.getProperty("browser"))) {
            browser = System.getProperty("browser");
            //in vm options (run configurations) -Dbrowser=chrome or  if from command line -P is used for parameter: for suite -Psuite=testng_not_parallel_run.xml
        } else if (StringUtils.isEmpty(browser) || "${browser}".equals(browser)) {
            browser = WebDriverProvider.DRIVER_DEFAULT;
        }
        String target = System.getProperty("target", "local");
        File file = new File(getClass().getResource(String.format("/props/%s.properties", target)).toURI());
        properties.load(new FileReader(file));

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
        baseUrl = properties.getProperty("web.baseUrl");
        if (StringUtils.isEmpty(baseUrl)) {
            baseUrl = URL_HOME;
        }
        if (currentUrl == null || !currentUrl.startsWith(baseUrl)) {
            getDriver().manage().window().maximize();
            getDriver().get(baseUrl);
            //
            AppManager appManager = new AppManager();
            threadLocalAppManager.set(appManager);
            appManager().init();
        }

        //assigning wait values to be used in explicit waits
        wait = new WebDriverWait(getDriver(), 10);
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        log.info(m.getName() + " with parameters " + Arrays.asList(p) + " - is started");
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        log.info(m.getName() + " - is complete");

    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        WebDriverProvider.quitDriver();
    }


}
