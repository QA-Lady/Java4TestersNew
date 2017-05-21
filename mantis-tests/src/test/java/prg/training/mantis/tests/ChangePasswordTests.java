package prg.training.mantis.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import prg.training.mantis.model.UserData;
import prg.training.mantis.model.Users;
import prg.training.mantis.tests.base.TestBase;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by QA Lady on 5/21/2017.
 */
public class ChangePasswordTests extends TestBase {
    Logger log = LoggerFactory.getLogger(ChangePasswordTests.class);

    @BeforeMethod
    public void startMailServer() {
        appManager().mail().start();
    }

    @Test
    public void ResetPasswordTest() throws IOException, MessagingException {
        long now = System.currentTimeMillis();
        String newUserPassword = String.format("password%s", now);
        Users users = appManager().dbHelper().users();
        UserData user = users.stream().filter((u) -> u.getId() != 1).collect(Collectors.toList()).iterator().next().withPassword(newUserPassword);
        UserData admin = users.stream().filter((u) -> u.getId() == 1).collect(Collectors.toList()).iterator().next().withPassword("root");
        log.info("Initiate Reset user password");
        appManager().changePasswHelper().resetPassword(admin, user);

        //click on confirmation link in email and change password
        appManager().changePasswHelper().confirmPasswordChange(user);

        Assert.assertTrue(appManager().newSession().login(user));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        appManager().mail().stop();
    }
}
