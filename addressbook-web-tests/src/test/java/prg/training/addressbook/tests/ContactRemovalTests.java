package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.ContactsData;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class ContactRemovalTests extends TestBase {

    private boolean firstRun = true;

    @Test(dataProvider = "Contact Index Provider")
    public void deleteContact(int index) {
        appManager.getNavigationHelper().goToHomePage(true);
        int contactsSize = appManager.getContactHelper().getContacts().size();
        if (firstRun == true && !appManager.getContactHelper().isContactPresent() || contactsSize < index) {
            for (int i = 0; i < index - contactsSize; i++) {
                String firstname = "firstname" + (i + 1);
                System.out.println("No contacts found or contacts size: " + contactsSize + " < " + index + " going to create contact with name: " + firstname);
                appManager.getContactHelper().createContact(new ContactsData(firstname, "lastname" + (i + 1), "address1", "1", "7324678090", "email1@ya.ru", null, "5", "December", "1975"), true);
            }
        }
        appManager.getContactHelper().deleteContact(index);
        appManager.getContactHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Record successful deleted");
        appManager.getNavigationHelper().goToHomePage(true);
        //
        firstRun = false;
    }

    @DataProvider(name = "Contact Index Provider")
    public static Object[][] indexProvider() {

        return new Object[][]{{2}, {3}, {5}};

    }

}
