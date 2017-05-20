package prg.training.addressbook.tests.dbTests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.dataModel.Contacts;
import prg.training.addressbook.utils.dataModel.ContactsData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class ContactRemovalTests extends TestBase {

    @BeforeMethod
    private void preconditionsPrep() {
        appManager().goTo().homePage(true);
        int index = 5;
        //getting contacts from DB
        int contactsSize = appManager().getDbHelper().contacts().size();
        if (contactsSize < index) {
            for (int i = 0; i <= index - contactsSize; i++) {
                String firstname = "firstname" + (i + 1);
                System.out.println("No contacts found or contacts size: " + contactsSize + " < " + index + " going to create contact with name: " + firstname);
                appManager().contactHelper().createContact(new ContactsData().withFirstname(firstname).withLastname("lastname" + (i + 1)).withAddress("address1").withHomeNumber(RandomStringUtils.randomNumeric(7)).withMobileNumber(RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(4)).withWorkNumber(RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(4)).withEmail("email1@ya.ru").withDay("5").withMonth("December").withYear("1975"), true);
            }
        }
    }

    @Test
    public void deleteContact() {
        //getting contacts from DB
        Contacts before = appManager().getDbHelper().contacts();
        ContactsData deletedContact = before.iterator().next();
        appManager().contactHelper().deleteAndCheckSuccess(deletedContact);
        // хеширование  - предварительная проверка при помощи более быстрой операции
        assertThat(appManager().contactHelper().getContactsCount(), equalTo(before.size() - 1));
        //getting contacts from DB
        Contacts after = appManager().getDbHelper().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
        verifyContactListInUI();
    }


    @DataProvider(name = "Contact Index Provider")
    public static Object[][] indexProvider() {

        return new Object[][]{{2}, {3}, {5}};

    }

}
