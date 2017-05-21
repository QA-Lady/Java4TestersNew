package prg.training.mantis.utils.appmanager;

import prg.training.mantis.tests.base.HelperBase;
import prg.training.mantis.utils.*;
import prg.training.mantis.utils.db.DbHelper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Created by QA Lady on 5/20/2017.
 */
public class AppManager {

    private HelperBase helperBase;

    private Properties properties = new Properties();
    private RegistrationHelper registrationHelper;
    private MailHelper mailHelper;
    private JamesHelper jamesHelper;
    private FtpHelper ftp;
    private DbHelper dbHelper;
    private ChangeMantisPasswHelper changePasswHelper;
    private SoapHelper soapHelper;

    public void init() throws URISyntaxException, IOException {

        String target = System.getProperty("target", "local");
        File file = new File(getClass().getResource(String.format("/props/%s.properties", target)).toURI());
        properties.load(new FileReader(file));
        //
        helperBase = new HelperBase(this);
    }

    public String getProperty(String propName) {
        return properties.getProperty(propName);
    }

    public HttpSession newSession() {
        //will initialize HttpSession with every request to newSession()
        return new HttpSession(this);
    }

    public RegistrationHelper registration() {
        //lazy initiation only when we call it
        if (registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public FtpHelper ftp() {
        if (ftp == null) {
            ftp = new FtpHelper(this);
        }
        return ftp;
    }

    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public JamesHelper james() {
        if (jamesHelper == null) {
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }

    public DbHelper dbHelper() {
        if (dbHelper == null) {
            dbHelper = new DbHelper();
        }
        return dbHelper;
    }

    public ChangeMantisPasswHelper changePasswHelper() {
        if (changePasswHelper == null) {
            changePasswHelper = new ChangeMantisPasswHelper(this);
        }
        return changePasswHelper;
    }

    public SoapHelper soapHelper() {
        if (soapHelper == null) {
            soapHelper = new SoapHelper(this);
        }
        return soapHelper;
    }

}
