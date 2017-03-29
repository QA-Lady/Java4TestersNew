package prg.training.addressbook.utils;

import org.openqa.selenium.NoAlertPresentException;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.utils.appManager.AppManager;
import prg.training.addressbook.utils.appManager.WebDriverProvider;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class AlertHelper extends HelperBase {


    public AlertHelper(AppManager appManager) {
        super(appManager);
    }

    public static boolean isAlertPresent() {
        try {
            WebDriverProvider.getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
