package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.utils.appManager.AppManager;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class NavigationHelper extends HelperBase {

    public NavigationHelper(AppManager appManager) {
        super(appManager);
    }

    public void goToHomePage() {
        clickOn(By.linkText("home page"));
    }

    public void goToGroupsPage() {
        clickOn(By.linkText("groups"));
    }

    public void returnToGroupPage() {
        clickOn(By.linkText("group page"));
    }


}
