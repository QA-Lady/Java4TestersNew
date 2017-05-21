package prg.training.mantis.utils;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prg.training.mantis.model.UserData;
import prg.training.mantis.tests.base.HelperBase;
import prg.training.mantis.utils.appmanager.AppManager;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by QA Lady on 5/20/2017.
 */
public class ChangeMantisPasswHelper extends HelperBase {
    Logger log = LoggerFactory.getLogger(ChangeMantisPasswHelper.class);

    public ChangeMantisPasswHelper(AppManager appManager) {
        super(appManager);
    }

    public void resetPassword(UserData admin, UserData user) {
        log.info("Login as administrator user");
        driver.get(appManager.getProperty("web.baseUrl") + "/login_page.php");
        enterText(By.name("username"), admin.getUsername());
        enterText(By.name("password"), admin.getPassword());
        clickOn(By.cssSelector("input[value='Login']"));
        log.info("Go to Manage Users page");
        clickOn(By.xpath("//a[contains(text(), 'Manage Users')]"));
        log.info("Click on the user selected to open Edit User page");
        clickOn(By.xpath(String.format("//a[contains(@href, 'manage_user_edit_page.php?user_id=%s')]", user.getId())));
        clickOn(By.xpath("//input[@value='Reset Password']"));
    }

    public void confirmPasswordChange(UserData user) throws IOException, MessagingException {
        log.info("Navigate to the confirmation change link");
        driver.get(appManager.mail().getConfirmationLink(1, user.getEmail()));
        log.info("Enter New password");
        enterText(By.name("password"), user.getPassword());
        log.info("Confirm New password");
        enterText(By.name("password_confirm"), user.getPassword());
        log.info("Click on 'Update User' button");
        clickOn(By.xpath("//input[@value='Update User']"));
    }


}
