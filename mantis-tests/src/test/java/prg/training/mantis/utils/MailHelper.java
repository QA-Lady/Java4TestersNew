package prg.training.mantis.utils;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import prg.training.mantis.model.MailMessage;
import prg.training.mantis.utils.appmanager.AppManager;
import ru.lanwen.verbalregex.VerbalExpression;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by QA Lady on 5/20/2017.
 */
public class MailHelper {


    private AppManager appManager;
    private final Wiser wiser;

    public MailHelper(AppManager appManager) {
        this.appManager = appManager;
        wiser = new Wiser();
    }

    public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + timeout) {
            if (wiser.getMessages().size() >= count) {
                return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Error("No mail or number of emails is less than expected :(");
    }

    public static MailMessage toModelMail(WiserMessage m) {
        try {
            MimeMessage mm = m.getMimeMessage();
            return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getConfirmationLink(int count, String email) throws IOException, MessagingException {
        List<MailMessage> mailMessages = waitForMail(count, 10000);
        return findConfirmationLink(mailMessages, email);
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    public void start() {
        wiser.start();
    }

    public void stop() {
        wiser.stop();
    }
}
