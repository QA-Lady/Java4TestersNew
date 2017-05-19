package prg.training.addressbook.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.dataModel.ContactsData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 5/14/2017.
 */
public class EmailTests extends TestBase {

    @BeforeMethod
    private void preconditionsPrep() {
        appManager().goTo().homePage(true);
        int index = 5;
        int contactsSize = appManager().contactHelper().getContactsCount();
        if (contactsSize < index) {
            for (int i = 0; i <= index - contactsSize; i++) {
                String firstname = "firstname" + (i + 1);
                System.out.println("No contacts found or contacts size: " + contactsSize + " < " + index + " going to create contact with name: " + firstname);
                appManager().contactHelper().createContact(new ContactsData().withFirstname(firstname).withLastname("lastname" + (i + 1)).withAddress("address1").withHomeNumber(RandomStringUtils.randomNumeric(7)).withMobileNumber(RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(4)).withWorkNumber(RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(4)).withEmail(RandomStringUtils.randomAlphanumeric(5) + "@" + RandomStringUtils.randomNumeric(4) + ".com").withDay("5").withMonth("December").withYear("1975"), true);
            }
        }
    }

    @Test
    public void testEmail() {
        appManager().goTo().homePage(true);
        ContactsData contact = appManager().contactHelper().allContacts().iterator().next();
        ContactsData contactInfoFromEditForm = appManager().contactHelper().infoFromEditForm(contact);
        System.out.println("check that email displayed on home page has a valid format");
        //valid eMail template
        Pattern p = Pattern.compile("([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)",
                Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        String email = contact.getEmail();
        //check that email from page matches valid email template
        Matcher m = p.matcher(email);
        assertThat(m.matches(), is(true));
        System.out.println("check that email from page equals to the one in contact edit page");
        assertThat(email, equalTo(contactInfoFromEditForm.getEmail()));
    }


}
