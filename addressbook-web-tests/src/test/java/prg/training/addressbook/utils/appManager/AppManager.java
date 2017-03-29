package prg.training.addressbook.utils.appManager;

import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.utils.*;

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

    public void init() {
        groupHelper = new GroupHelper(this);
        navigationHelper = new NavigationHelper(this);
        contactHelper = new ContactHelper(this);
        loginHelper = new LoginHelper(this);
        alertHelper = new AlertHelper(this);
        helperBase = new HelperBase(this);
    }

    public GroupHelper getGroupHelper() {
        return groupHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }

    public ContactHelper getContactHelper() {
        return contactHelper;
    }

    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    public AlertHelper getAlertHelper() {
        return alertHelper;
    }


}
