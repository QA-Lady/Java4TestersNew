package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.ContactsData;
import prg.training.addressbook.utils.appManager.AppManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class ContactHelper extends HelperBase {


    public ContactHelper(AppManager appManager) {
        super(appManager);
    }

    public void completeContactsForm(ContactsData contactsData) {
        //Fill Contact's form
        enterText(By.name("firstname"), contactsData.getFirstname());
        enterText(By.name("lastname"), contactsData.getLastname());
        enterText(By.name("address"), contactsData.getAddress());
        enterText(By.name("home"), contactsData.getHomeNumber());
        enterText(By.name("mobile"), contactsData.getPhoneNumber());
        enterText(By.name("email"), contactsData.getEmail());
        enterBirthdayDate(contactsData.getDay(), contactsData.getMonth(), contactsData.getYear());
    }

    public void initContactCreation() {
        //invoke Contact creation dialog
        clickOn(By.linkText("add new"));
    }

    protected void enterBirthdayDate(String day, String month, String year) {
        new Select(getElement(By.name("bday"))).selectByVisibleText(day);
        new Select(getElement(By.name("bmonth"))).selectByVisibleText(month);
        enterText(By.name("byear"), year);
    }

    public void selectGroup4Contact(ContactsData contactsData, boolean contactCreation) {
        String groupID = contactsData.getGroupID();
        if (groupID == null) {
            return;
        } else {
            if (contactCreation) {
                Select groupSelect = new Select(getElement(By.name("new_group")));
                groupSelect.selectByVisibleText(groupID);
            } else {
                Assert.assertFalse(isElementPresent(TestBase.getDriver(), By.name("new_group")));
            }
        }
    }

    public void initNextContactCreation() {
        //create one more contact from successful contact creation message link
        clickOn(By.linkText("add next"));
    }

    public void deleteContact(int index) {
        clickOn(By.xpath("//tr[" + index + "]" + "//input[@name='selected[]']"));
        //click on Delete button
        WebElement deleteBtn = getElement(By.xpath("//input[@value='Delete']"));
        clickOn(deleteBtn);
        appManager.getAlertHelper().acceptAlert();
    }


    public void editContactName(int index, String name) {
        clickOn(By.xpath("//tr[@name='entry'][" + (index) + "]" + "//input[@name='selected[]']"));
        //invoke Contact Edit
        clickOn(By.xpath("//tr[@name='entry'][" + (index) + "]" + "/td[7]/a/img"));
        WebElement modifyBtn = getElement(By.xpath("//input[@value='Modify']"));
        clickOn(modifyBtn);
        enterText(By.name("firstname"), name);

    }

    public void editContact(int index, ContactsData contactsData) {
        clickOn(By.xpath("//tr[@name='entry'][" + (index) + "]" + "//input[@name='selected[]']"));
        //invoke Contact Edit
        clickOn(By.xpath("//tr[@name='entry'][" + (index) + "]" + "/td[7]/a/img"));
        WebElement modifyBtn = getElement(By.xpath("//input[@value='Modify']"));
        clickOn(modifyBtn);
        appManager.getContactHelper().completeContactsForm(contactsData);

    }

    public void createContact(ContactsData contactsData, boolean goToHomePage) {
        appManager.getContactHelper().initContactCreation();
        appManager.getContactHelper().completeContactsForm(contactsData);
        appManager.getContactHelper().selectGroup4Contact(contactsData, true);
        appManager.getContactHelper().submit();
        if (goToHomePage) {
            appManager.getNavigationHelper().goToHomePage(false);
        }
    }

    public boolean isContactPresent() {
        return isElementPresent(TestBase.getDriver(), By.xpath("//tr[@name='entry']//input[@name='selected[]']"));
    }

    public List<WebElement> getContacts() {
        List<WebElement> contacts = TestBase.getDriver().findElements(By.xpath("//tr[@name='entry']//input[@name='selected[]']"));
        return contacts;
    }

    public List<ContactsData> getContactList() {
        List<ContactsData> contacts = new ArrayList<ContactsData>();
        List<WebElement> rows = TestBase.getDriver().findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement row : rows) {
            String name = row.findElement(By.xpath("./td[3]")).getText();
            String lastname = row.findElement(By.xpath("./td[2]")).getText();
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
            ContactsData contact = new ContactsData(id, name, lastname, null, null, null, null, null, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }

}
