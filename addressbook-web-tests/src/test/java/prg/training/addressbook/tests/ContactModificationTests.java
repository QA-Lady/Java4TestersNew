package prg.training.addressbook.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.ContactsData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class ContactModificationTests extends TestBase {


    @Test(dataProvider = "Contact Name Provider")
    public void editContact(int index) {
        appManager.getNavigationHelper().goToHomePage(true);
        int contactsSize = appManager.getContactHelper().getContacts().size();
        if (!appManager.getContactHelper().isContactPresent() || contactsSize < index) {
            for (int i = 0; i <= index - contactsSize; i++) {
                String firstname = "firstname" + (i + 1);
                System.out.println("No contacts found or contacts size: " + contactsSize + " < " + index + " going to create contact with name: " + firstname);
                appManager.getContactHelper().createContact(new ContactsData(firstname, "lastname" + (i + 1), "address1", "1", "7324678090", "email1@ya.ru", null, "5", "December", "1975"), true);
            }
        }
        List<ContactsData> before = appManager.getContactHelper().getContactList();
        ContactsData contact = new ContactsData("new firstname" + (index + 1), "new lastname" + (index + 1), "new address", RandomStringUtils.randomNumeric(2), RandomStringUtils.randomNumeric(11), RandomStringUtils.randomAlphanumeric(5) + "@ya.ru", null, "5", "December", "1975");

        appManager.getContactHelper().editContact(index, contact);
        appManager.getContactHelper().update();
        appManager.getContactHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Address book updated\nreturn to home page");
        appManager.getNavigationHelper().goToHomePage(false);
        List<ContactsData> after = appManager.getContactHelper().getContactList();

        //available starting from java 8
        int id = before.get(index - 1/*xpath starts index from 1 and array list starts from 0*/).getContactID();
        before.remove(index - 1/*xpath starts index from 1 and array list starts from 0*/);

        contact.setContactID(id);
        before.add(contact);

//        sorting approach starting from java 8
        Comparator<? super ContactsData> byId = Comparator.comparingInt(ContactsData::getContactID);

        before.sort(byId);
        after.sort(byId);

        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(after, before);

    }

    @DataProvider(name = "Contact Name Provider")
    public static Object[][] nameProvider() {

        return new Object[][]{{3}, {5}, {8}};
    }


}
