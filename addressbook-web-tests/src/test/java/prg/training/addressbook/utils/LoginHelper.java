package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.appManager.AppManager;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class LoginHelper extends HelperBase {

    public LoginHelper(AppManager appManager) {
        super(appManager);
    }

    public void loginIfNotLoggedIn(String username, String password) {
        WebElement logoutElement = null;
        try {
            logoutElement = TestBase.getDriver().findElement(By.xpath("//form/a[text()='Logout']"));
            return;
        } catch (Exception e) {
            enterText(By.name("user"), username);
            enterText(By.name("pass"), password);
            clickOn(By.xpath("//input[@value='Login']"));
        }

    }
}
