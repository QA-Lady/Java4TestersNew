package prg.training.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.ContactsData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by QA Lady on 3/27/2017.
 */
public class ContactsCreationTests extends TestBase {

    @Test(dataProvider = "ContactsInfo")
    public void addContactsTest(ContactsData contactsData) throws Exception {

        appManager.getNavigationHelper().goToHomePage(true);

        List<ContactsData> before = appManager.getContactHelper().getContactList();

        appManager.getContactHelper().createContact(contactsData, false);
//        appManager.getContactHelper().initNextContactCreation();
//        appManager.getContactHelper().submit();
        appManager.getNavigationHelper().goToHomePage(false);

        List<ContactsData> after = appManager.getContactHelper().getContactList();
        //available starting from java 8
        contactsData.setContactID(after.stream().max(Comparator.comparingInt(ContactsData::getContactID)).get().getContactID());
        before.add(contactsData);
        //sorting approach before java 8
//        Collections.sort(before);
//        Collections.sort(after);

        //sorting approach starting from java 8
        Comparator<? super ContactsData> byId = Comparator.comparingInt(ContactsData::getContactID);

        //comparator by name and then by last name
//        Comparator<? super ContactsData> byName = (o1, o2) -> {
//            int res = o1.getFirstname().compareTo(o2.getFirstname());
//            return res == 0 ? o1.getLastname().compareTo(o2.getLastname()) : res;
//        };

        before.sort(byId);
        after.sort(byId);
        System.out.println(before);
        System.out.println(after);

        Assert.assertEquals(after, before);

    }

    @DataProvider(name = "ContactsInfo")
    public static Object[][] text() {

        return new Object[][]{{new ContactsData("firstname1", "lastname1", "address1", "1", "7324678090", "email1@ya.ru", "Group 1", "5", "December", "1975")}, {new ContactsData("firstname2", "lastname2", "address2", "2", "4446632467", "email2@ya.ru", "Group 2", "31", "August", "2005")}, {new ContactsData("firstname3", "lastname3", "address3", "3", "4446632467", "email3@ya.ru", "Group 3", "18", "February", "1967")}};

    }
}
