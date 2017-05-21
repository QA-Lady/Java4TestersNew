package prg.training.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import prg.training.mantis.model.MailMessage;
import prg.training.mantis.tests.base.TestBase;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Created by @A Lady on 5/20/2017.
 */
public class RegistrationTest extends TestBase {


    @BeforeMethod
    public void startMailServer() {
        appManager().mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        long now = System.currentTimeMillis();
        String user = String.format("user%s", now);
        String password = "password";
//        username will have user+currentTimeMillis
        String email = String.format("user%s@localhost.localdomain", now);
//        appManager().james().createUser(user, password);
        appManager().registration().start(user, email);
        List<MailMessage> mailMessages = appManager().mail().waitForMail(2, 10000);
        //use  james instead of subethamail
//        List<MailMessage> mailMessages = appManager().james().waitForMail(user, password, 60000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        appManager().registration().finish(confirmationLink, password);
        Assert.assertTrue(appManager().newSession().login(user, password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();//the following regex will be generated - "(?:http\:\/\/)(?:\S)+"
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        appManager().mail().stop();
    }
}
