package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.appManager.AppManager;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class NavigationHelper extends HelperBase {

    public static final String URL_HOME = "http://localhost/addressbook/";

    public NavigationHelper(AppManager appManager) {
        super(appManager);
    }

    public void homePage(boolean goFromMainPage) {
        if (onHomePage()) {
            return;
        }

        if (goFromMainPage) {
            clickOn(By.linkText("home"));
        } else {
            //return to Groups page from success message link
            clickOn(By.linkText("home page"));
        }

        TestBase.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("maintable")));

    }

    private boolean onHomePage() {
        if (TestBase.getDriver().getCurrentUrl().equals(URL_HOME) && isElementPresent(TestBase.getDriver(), By.id("maintable"))) {
            return true;
        }
        return false;
    }


    public void groupsPage(boolean goFromMainPage) {
        if (!onGroupsPage()) {
            if (goFromMainPage) {
                clickOn(By.linkText("groups"));
            } else {
                //return to Groups page from success message link
                clickOn(By.linkText("group page"));
            }
        }
    }

    private boolean onGroupsPage() {
        if (TestBase.getDriver().getCurrentUrl().contains("/group.php") && isElementPresent(TestBase.getDriver(), By.name("new"))) {
            return true;
        }
        return false;
    }

}
