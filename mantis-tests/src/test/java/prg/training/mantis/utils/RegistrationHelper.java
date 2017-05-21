package prg.training.mantis.utils;

import org.openqa.selenium.By;
import prg.training.mantis.tests.base.HelperBase;
import prg.training.mantis.utils.appmanager.AppManager;

/**
 * Created by QA Lady on 5/20/2017.
 */
public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(AppManager appManager) {
        super(appManager);
    }

    public void start(String username, String email) {
        driver.get(appManager.getProperty("web.baseUrl") + "/signup_page.php");
        enterText(By.name("username"), username);
        enterText(By.name("email"), email);
        clickOn(By.cssSelector("input[value='Signup']"));
    }

    public void finish(String confirmationLink, String password) {
        driver.get(confirmationLink);
        enterText(By.name("password"), password);
        enterText(By.name("password_confirm"), password);
        clickOn(By.cssSelector("input[value='Update User']"));
    }

}
