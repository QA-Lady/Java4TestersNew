package prg.training.mantis.tests.base;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.SkipException;
import org.testng.annotations.*;
import prg.training.mantis.utils.appmanager.AppManager;
import prg.training.mantis.utils.appmanager.WebDriverProvider;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * Created by QA Lady on 5/20/2017.
 */
public abstract class TestBase {

    Logger log = LoggerFactory.getLogger(TestBase.class);

    protected static final ThreadLocal<AppManager> threadLocalAppManager = new ThreadLocal<AppManager>();
    protected static ThreadLocal<String> threadLocalBrowser = new ThreadLocal<>();
    public static WebDriverWait wait;
    public static String baseUrl;
    public static final String URL_HOME = "http://localhost/mantisbt-1.2.20/";
    public static String currentUrl = null;
    private static String browser;

    public static WebDriver getDriver() {
        WebDriver driver = WebDriverProvider.getDriver(threadLocalBrowser.get(), false);
        //assigning wait values to be used in explicit waits
        wait = new WebDriverWait(driver, 10);
        return driver;
    }

    public static void getBaseUrl() {
        if (StringUtils.isNotEmpty(System.getProperty("browser"))) {
            browser = System.getProperty("browser");
            //in vm options (run configurations) -Dbrowser=chrome or  if from command line -P is used for parameter: for suite -Psuite=testng_not_parallel_run.xml
        } else if (StringUtils.isEmpty(browser) || "${browser}".equals(browser)) {
            browser = WebDriverProvider.DRIVER_DEFAULT;
        }

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
        if (currentUrl == null || !currentUrl.startsWith(baseUrl)) {
            getDriver().manage().window().maximize();
            getDriver().get(baseUrl);
        }
    }

    public AppManager appManager() {
        return threadLocalAppManager.get();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional String browser) throws Exception {
        this.browser = browser;
        AppManager appManager = new AppManager();
        threadLocalAppManager.set(appManager);
        appManager().init();
        //
        baseUrl = appManager().getProperty("web.baseUrl");
        if (StringUtils.isEmpty(baseUrl)) {
            baseUrl = URL_HOME;
        }
        appManager().ftp().upload(new File("mantis-tests/src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");

    }

    public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    public boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = appManager().soapHelper().getMantisConnect();
        IssueData issueData = mc.mc_issue_get(appManager().getProperty("web.adminLogin"), appManager().getProperty("web.adminPassword"),
                BigInteger.valueOf(issueId));
        return !(issueData.getStatus().getName().equals("closed") || issueData.getStatus().getName().equals("resolved"));

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
    public void tearDown() throws IOException {
        appManager().ftp().restore("config_inc.php.bak", "config_inc.php");
        WebDriverProvider.quitDriver();
    }


}
