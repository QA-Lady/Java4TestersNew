package prg.training.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.Contacts;
import prg.training.addressbook.utils.DataModel.ContactsData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 3/27/2017.
 */
public class ContactsCreationTests extends TestBase {

    @Test(dataProvider = "ContactsInfo")
    public void addContactsTest(ContactsData contactsData) throws Exception {

        appManager().goTo().homePage(true);

        Contacts before = appManager().contactHelper().allContacts();

        appManager().contactHelper().createContact(contactsData, false);
        appManager().goTo().homePage(false);

        Contacts after = appManager().contactHelper().allContacts();

        assertThat(after, equalTo(before.withAdded(contactsData.withContactID(after.stream().mapToInt((c) -> c.getContactID()).max().getAsInt()))));

    }

    @DataProvider(name = "ContactsInfo")
    public static Object[][] text() {

        return new Object[][]{{new ContactsData().withFirstname("firstname1").withLastname("lastname1").withAddress("address1").withHomeNumber("1").withPhoneNumber("7324678090").withEmail("email1@ya.ru").withGroup("Group 1").withDay("5").withMonth("December").withYear("1975")}, {new ContactsData().withFirstname("firstname2").withLastname("lastname2").withAddress("address2").withHomeNumber("2").withPhoneNumber("4446632467").withEmail("email2@ya.ru").withGroup("Group 2").withDay("31").withMonth("August").withYear("2005")}, {new ContactsData().withFirstname("firstname3").withLastname("lastname3").withAddress("address3").withHomeNumber("3").withPhoneNumber("456797123588").withEmail("email3@ya.ru").withGroup("Group 3").withDay("18").withMonth("February").withYear("1962")}};

    }
}
