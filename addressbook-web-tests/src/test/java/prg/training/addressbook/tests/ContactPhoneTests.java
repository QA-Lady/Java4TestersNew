package prg.training.addressbook.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.dataModel.ContactsData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 5/14/2017.
 */
public class ContactPhoneTests extends TestBase {

    @BeforeMethod
    private void preconditionsPrep() {
        appManager().goTo().homePage(true);
        int index = 5;
        int contactsSize = appManager().contactHelper().getContactsCount();
        if (contactsSize < index) {
            for (int i = 0; i <= index - contactsSize; i++) {
                String firstname = "firstname" + (i + 1);
                System.out.println("No contacts found or contacts size: " + contactsSize + " < " + index + " going to create contact with name: " + firstname);
                appManager().contactHelper().createContact(new ContactsData().withFirstname(firstname).withLastname("lastname" + (i + 1)).withAddress("address1").withHomeNumber(RandomStringUtils.randomNumeric(7)).withMobileNumber(RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(4)).withWorkNumber(RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(4)).withEmail("email1@ya.ru").withDay("5").withMonth("December").withYear("1975"), true);
            }
        }
    }

    @Test
    public void testContactPhones() {
        appManager().goTo().homePage(true);
        ContactsData contact = appManager().contactHelper().allContacts().iterator().next();
        ContactsData contactInfoFromEditForm = appManager().contactHelper().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergedPhones(contactInfoFromEditForm)));
    }

    public String mergedPhones(ContactsData contact) {
        return Arrays.asList(contact.getHomeNumber(), contact.getMobileNumber(), contact.getWorkNumber())
                .stream().filter((s -> !s.equals("")))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));


////        without lambdas and functional programming
//        String result = "";
//                if(contact.getHomeNumber()!=null){
//                    result = result + contact.getHomeNumber();
//                }else if(contact.getMobileNumber()!=null){
//                    result = result + contact.getMobileNumber();
//                }else if(contact.getWorkNumber()!=null){
//                    result = result + contact.getWorkNumber();
//                }
//                return result;


    }


    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
