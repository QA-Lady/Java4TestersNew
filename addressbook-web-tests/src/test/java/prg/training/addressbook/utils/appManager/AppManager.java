package prg.training.addressbook.utils.appManager;

import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.utils.*;
import prg.training.addressbook.utils.db.DbHelper;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class AppManager {

    private GroupHelper groupHelper;
    private NavigationHelper navigationHelper;
    private ContactHelper contactHelper;
    private LoginHelper loginHelper;
    private AlertHelper alertHelper;
    private HelperBase helperBase;
    private DbHelper dbHelper;



    public void init() {
        dbHelper = new DbHelper();
        groupHelper = new GroupHelper(this);
        navigationHelper = new NavigationHelper(this);
        contactHelper = new ContactHelper(this);
        loginHelper = new LoginHelper(this);

        alertHelper = new AlertHelper(this);
        helperBase = new HelperBase(this);

    }

    public GroupHelper groupHelper() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contactHelper() {
        return contactHelper;
    }

    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    public AlertHelper getAlertHelper() {
        return alertHelper;
    }

    public DbHelper getDbHelper() {
        return dbHelper;
    }


}
