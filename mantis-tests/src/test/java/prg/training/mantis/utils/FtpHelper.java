package prg.training.mantis.utils;

import org.apache.commons.net.ftp.FTPClient;
import prg.training.mantis.utils.appmanager.AppManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by QA Lady on 5/20/2017.
 */
public class FtpHelper {
    private final AppManager appManager;
    private FTPClient ftp;

    public FtpHelper(AppManager appManager) {
        this.appManager = appManager;
        ftp = new FTPClient();
    }

    public void upload(File file, String target, String backup) throws IOException {
        ftp.connect(appManager.getProperty("ftp.host"));
        ftp.login(appManager.getProperty("ftp.login"), appManager.getProperty("ftp.password"));
        ftp.deleteFile(backup);
        ftp.rename(target, backup);
        ftp.enterLocalPassiveMode();
        ftp.storeFile(target, new FileInputStream(file));
        ftp.disconnect();
    }

    public void restore(String backup, String target) throws IOException {
        ftp.connect(appManager.getProperty("ftp.host"));
        ftp.login(appManager.getProperty("ftp.login"), appManager.getProperty("ftp.password"));
        ftp.deleteFile(target);
        ftp.rename(backup, target);
        ftp.disconnect();
    }

}
