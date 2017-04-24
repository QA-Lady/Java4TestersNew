package prg.training.addressbook.utils;

import org.openqa.selenium.NoAlertPresentException;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.appManager.AppManager;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class AlertHelper extends HelperBase {


    public AlertHelper(AppManager appManager) {
        super(appManager);
    }

    public static boolean isAlertPresent() {
        try {
            TestBase.getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public static void acceptAlert() {
        if (isAlertPresent()) {
            TestBase.getDriver().switchTo().alert().accept();
        }
    }

    public static void dismissAlert() {
        if (isAlertPresent()) {
            TestBase.getDriver().switchTo().alert().dismiss();
        }
    }
}
