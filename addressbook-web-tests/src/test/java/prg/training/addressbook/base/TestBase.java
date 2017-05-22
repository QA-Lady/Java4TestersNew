package prg.training.addressbook.base;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import prg.training.addressbook.utils.NavigationHelper;
import prg.training.addressbook.utils.appManager.AppManager;
import prg.training.addressbook.utils.appManager.WebDriverProvider;
import prg.training.addressbook.utils.dataModel.Contacts;
import prg.training.addressbook.utils.dataModel.ContactsData;
import prg.training.addressbook.utils.dataModel.GroupData;
import prg.training.addressbook.utils.dataModel.Groups;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 3/26/2017.
 */
public abstract class TestBase {
    Logger log = LoggerFactory.getLogger(TestBase.class);

    protected static final ThreadLocal<AppManager> threadLocalAppManager = new ThreadLocal<AppManager>();
    protected static ThreadLocal<String> threadLocalBrowser = new ThreadLocal<>();
    public static WebDriverWait wait;
    public static Properties properties = new Properties();
    public static String baseUrl;
    public static String target;

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
        target = System.getProperty("target", "local");
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
            baseUrl = NavigationHelper.URL_HOME;
        }
        if (currentUrl == null || !currentUrl.startsWith(baseUrl)) {
            getDriver().manage().window().maximize();
            getDriver().get(baseUrl);
            //
            AppManager appManager = new AppManager();
            threadLocalAppManager.set(appManager);
            appManager().init();
            appManager().getLoginHelper().loginIfNotLoggedIn(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
            appManager().goTo().homePage(true);
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


    public void verifyGroupListInUI() {
//        from edit configuration set -DverifyUI=true in the VM options to run this check
        //makes the system setting to be boolean
        if (Boolean.getBoolean("verifyUI")) {
            //getting groups from DB
            Groups dbGroups = appManager().getDbHelper().groups();
            //getting groups from GUI
            Groups uIGroups = appManager().groupHelper().allGroups();
            //with lambda function we are updating group object from DB to contain only Name & ID
            // since it's the only thing available on Groups page (no header or footer
            assertThat(uIGroups, equalTo(dbGroups.stream().map((g) -> new GroupData().withGroupID(g.getGroupID()).withGroupName(g.getGroupName())).collect(Collectors.toSet())));
        }
    }

    public void verifyContactListInUI() {
//        from edit configuration set -DverifyUI=true in the VM options to run this check
        //makes the system setting to be boolean
        if (Boolean.getBoolean("verifyUI")) {
            //getting contacts from DB
            Contacts dbContacts = appManager().getDbHelper().contacts();
            //getting contacts from GUI
            Contacts uIContacts = appManager().contactHelper().allContacts();
            // need to check only those values from db that are available in gui, and don't have allPhones in uIContact
            assertThat(uIContacts.stream().map((c) -> new ContactsData().withContactID(c.getContactID()).withFirstname(c.getFirstname()).withLastname(c.getLastname()).withAddress(c.getAddress()).withEmail(c.getEmail())).collect(Collectors.toSet()), equalTo(dbContacts.stream().map((c) -> new ContactsData().withContactID(c.getContactID()).withFirstname(c.getFirstname()).withLastname(c.getLastname()).withAddress(c.getAddress()).withEmail(c.getEmail())).collect(Collectors.toSet())));
        }
    }
}
