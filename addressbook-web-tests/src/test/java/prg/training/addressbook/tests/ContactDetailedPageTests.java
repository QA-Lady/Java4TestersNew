package prg.training.addressbook.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.ContactsData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 5/14/2017.
 */
public class ContactDetailedPageTests extends TestBase {

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
    public void contactDetailsTest() {
        appManager().goTo().homePage(true);
        ContactsData contact = appManager().contactHelper().allContacts().iterator().next();
        ContactsData contactInfoFromEditForm = appManager().contactHelper().infoFromEditForm(contact);
        ContactsData infoFromContactDetails = appManager().contactHelper().infoFromContactDetails(contact);
        System.out.println("check that name from Details page equals to the one in contact edit page");
        assertThat(infoFromContactDetails.getFirstname(), equalTo(contactInfoFromEditForm.getFirstname()));
        System.out.println("check that lastname from Details page equals to the one in contact edit page");
        assertThat(infoFromContactDetails.getLastname(), equalTo(contactInfoFromEditForm.getLastname()));
        System.out.println("check that email from Details page equals to the one in contact edit page");
        assertThat(infoFromContactDetails.getEmail(), equalTo(contactInfoFromEditForm.getEmail()));
        System.out.println("check that all phones from Details page equals to the one in contact edit page");
//        assertThat(ContactPhoneTests.cleaned(infoFromContactDetails.getAllPhones()).replace("H:", "").replace("M:", "\n").replace("W:", "\n"), equalTo(new ContactPhoneTests().mergedPhones(contactInfoFromEditForm)));
        assertThat(ContactPhoneTests.cleaned(infoFromContactDetails.getAllPhones()).replaceAll("[HMW]:", "\n").replaceFirst("\n", ""), equalTo(new ContactPhoneTests().mergedPhones(contactInfoFromEditForm)));
    }


}
