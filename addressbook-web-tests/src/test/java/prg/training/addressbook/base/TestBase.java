package prg.training.addressbook.base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import prg.training.addressbook.utils.appManager.AppManager;
import prg.training.addressbook.utils.appManager.WebDriverProvider;

/**
 * Created by QA Lady on 3/26/2017.
 */
public class TestBase {

    protected final AppManager appManager = new AppManager();

    @BeforeSuite
    public void setUp() throws Exception {
        WebDriverProvider.configDriver();
        appManager.init();
        //
        WebDriverProvider.getDriver().manage().window().maximize();
        WebDriverProvider.getDriver().get("http://localhost/addressbook/");
    }

    @BeforeTest
    public void doLogin() {
        appManager.getLoginHelper().loginIfNotLoggedIn("admin", "secret");
    }

    @AfterSuite
    public void tearDown() {
        WebDriverProvider.getDriver().quit();
    }


}
