package prg.training.addressbook.tests;

import org.apache.commons.lang3.RandomStringUtils;
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
        // хеширование  - предварительная проверка при помощи более быстрой операции
        assertThat(appManager().contactHelper().getContactsCount(), equalTo(before.size() + 1));
        Contacts after = appManager().contactHelper().allContacts();
        System.out.println("EXPECTED: " + before.withAdded(contactsData.withContactID(after.stream().mapToInt((c) -> c.getContactID()).max().getAsInt())));
        System.out.println(after);
        assertThat(after, equalTo(before.withAdded(contactsData.withContactID(after.stream().mapToInt((c) -> c.getContactID()).max().getAsInt()))));

    }

    @DataProvider(name = "ContactsInfo")
    public static Object[][] text() {

        return new Object[][]{{new ContactsData().withFirstname("firstname1").withLastname("lastname1").withAddress("address1").withHomeNumber("1").withMobileNumber("7324678090").withWorkNumber(RandomStringUtils.randomNumeric(12)).withEmail("email1@ya.ru").withGroup("Group 1").withDay("5").withMonth("December").withYear("1975")}, {new ContactsData().withFirstname("firstname2").withLastname("lastname2").withAddress("address2").withHomeNumber("2").withMobileNumber("4446632467").withWorkNumber(RandomStringUtils.randomNumeric(12)).withEmail("email2@ya.ru").withGroup("Group 2").withDay("31").withMonth("August").withYear("2005")}, {new ContactsData().withFirstname("firstname3").withLastname("lastname3").withAddress("address3").withHomeNumber("3").withMobileNumber("456797123588").withWorkNumber(RandomStringUtils.randomNumeric(12)).withEmail("email3@ya.ru").withGroup("Group 3").withDay("18").withMonth("February").withYear("1962")}};

    }
}
