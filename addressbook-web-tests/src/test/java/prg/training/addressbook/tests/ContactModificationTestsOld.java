package prg.training.addressbook.tests;

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
public class ContactModificationTestsOld extends TestBase {

//    this class uses more complex comparator by name and then by lastname


    @Test(dataProvider = "Contact Name Provider", enabled = false)
    public void editContact(int index, String name) {
        appManager().goTo().homePage(true);
        int contactsSize = appManager().contactHelper().getContacts().size();
        if (!appManager().contactHelper().isContactPresent() || contactsSize < index) {
            for (int i = 0; i <= index - contactsSize; i++) {
                String firstname = "firstname" + (i + 1);
                System.out.println("No contacts found or contacts size: " + contactsSize + " < " + index + " going to create contact with name: " + firstname);
//                appManager.contactHelper().createContact(new ContactsData(firstname, "lastname" + (i + 1), "address1", "1", "7324678090", "email1@ya.ru", null, "5", "December", "1975"), true);
            }
        }
        List<ContactsData> before = appManager().contactHelper().getContactList();
        appManager().contactHelper().editContactName(index, name);
        appManager().contactHelper().update();
        appManager().contactHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Address book updated\nreturn to home page");
        appManager().goTo().homePage(false);
        List<ContactsData> after = appManager().contactHelper().getContactList();

        //available starting from java 8
        before.get(index - 1/*xpath starts index from 1 and array list starts from 0*/).withFirstname(name); //using editGroupTest instead of remove and add

        //sorting approach before java 8
//        Collections.sort(before);
//        Collections.sort(after);


        //sorting approach starting from java 8
        Comparator<? super ContactsData> byName = (o1, o2) -> {
            int res = o1.getFirstname().compareTo(o2.getFirstname());
            return res == 0 ? o1.getLastname().compareTo(o2.getLastname()) : res;
        };
        before.sort(byName);
        after.sort(byName);

        Assert.assertEquals(after, before);

    }

    @DataProvider(name = "Contact Name Provider")
    public static Object[][] nameProvider() {

        return new Object[][]{{3, "new name1"}, {5, "new name2"}, {8, "new name3"}};
//        return new Object[][]{{new ContactsData("firstname1", "lastname1", "address1", "1", "7324678090", "email1@ya.ru", "Group 1", "5", "December", "1975")}, {new ContactsData("firstname2", "lastname2", "address2", "2", "4446632467", "email2@ya.ru", "Group 2", "31", "August", "2005")}, {new ContactsData("firstname3", "lastname3", "address3", "3", "4446632467", "email3@ya.ru", "Group 3", "18", "February", "1967")}};

    }


}
