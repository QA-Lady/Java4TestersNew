package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.Contacts;
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
        enterText(By.name("mobile"), contactsData.getMobileNumber());
        enterText(By.name("work"), contactsData.getWorkNumber());
        enterText(By.name("email"), contactsData.getEmail());
        enterBirthdayDate(contactsData.getDay(), contactsData.getMonth(), contactsData.getYear());
    }

    public void editAndCheckSuccess(ContactsData contact) {
        editContactById(contact);
        update();
        checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Address book updated\nreturn to home page");
        contactsCache = null;
        appManager.goTo().homePage(false);
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
        String groupID = contactsData.getGroup();
        if (groupID != null) {
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
        clickOn(By.xpath("//tr[@name='entry'][" + (index) + "]" + "//input[@name='selected[]']"));
        int id = Integer.parseInt(getElement(By.xpath("//tr[@name='entry'][" + (index) + "]" + "//input[@name='selected[]']")).getAttribute("value"));
        System.out.println("Going to delete contact with contact ID: " + id);
        //click on Delete button
        WebElement deleteBtn = getElement(By.xpath("//input[@value='Delete']"));
        clickOn(deleteBtn);
        appManager.getAlertHelper().acceptAlert();
    }

    public void deleteContactById(int id) {
        clickOn(By.xpath("//tr[@name='entry']//input[@value='" + id + "']"));
        System.out.println("Going to delete contact with contact ID: " + id);
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


    public void editContactById(ContactsData contact) {
        int id = contact.getContactID();
        clickOnEditContact(id);
        WebElement modifyBtn = getElement(By.xpath("//input[@value='Modify']"));
        clickOn(modifyBtn);
        appManager.contactHelper().completeContactsForm(contact);

    }

    public void clickOnEditContact(int id) {
        //select checkbox
        clickOn(By.xpath("//tr[@name='entry']//input[@value='" + id + "']"));
        //invoke Contact Edit
        clickOn(By.xpath("//tr[@name='entry']//input[@value='" + id + "']" + "/../..//td[7]/a/img"));
        //another option would be:
//        WebElement checkbox = getElement(By.cssSelector(String.format("input[@value='%s']",id)));
//        WebElement row = checkbox.findElement(By.xpath("./../.."));
//        List<WebElement> cells = row.findElements(By.tagName("td"));
//        clickOn(cells.get(7).findElement(By.tagName("a")));
    }

    public void createContact(ContactsData contactsData, boolean goToHomePage) {
        initContactCreation();
        completeContactsForm(contactsData);
        selectGroup4Contact(contactsData, true);
        submit();
        contactsCache = null;
        if (goToHomePage) {
            appManager.goTo().homePage(false);
        }
    }

    public void deleteAndCheckSuccess(ContactsData contact) {
        deleteContactById(contact.getContactID());
        checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Record successful deleted");
        contactsCache = null;
        appManager.goTo().homePage(true);
    }

    public boolean isContactPresent() {
        return isElementPresent(TestBase.getDriver(), By.xpath("//tr[@name='entry']//input[@name='selected[]']"));
    }

    public int getContactsCount() {
        return TestBase.getDriver().findElements(By.xpath("//tr[@name='entry']//input[@name='selected[]']")).size();
    }


    public List<ContactsData> getContactList() {
        List<ContactsData> contacts = new ArrayList<ContactsData>();
        List<WebElement> rows = TestBase.getDriver().findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement row : rows) {
            String name = row.findElement(By.xpath("./td[3]")).getText();
            String lastname = row.findElement(By.xpath("./td[2]")).getText();
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
            ContactsData contact = new ContactsData().withContactID(id).withFirstname(name).withLastname(lastname);
            contacts.add(contact);
        }
        return contacts;
    }

    private Contacts contactsCache = null;

    public Contacts allContacts() {
        if (contactsCache != null) {
            return new Contacts(contactsCache);
        }
        contactsCache = new Contacts();
        List<WebElement> rows = TestBase.getDriver().findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement row : rows) {
            String name = row.findElement(By.xpath("./td[3]")).getText();
            String lastname = row.findElement(By.xpath("./td[2]")).getText();
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
            String[] allPhones = row.findElement(By.xpath("./td[6]")).getText().split("\n");
            ContactsData contact = new ContactsData().withContactID(id).withFirstname(name).withLastname(lastname).withHomeNumber(allPhones[0]).withMobileNumber(allPhones[1]).withWorkNumber(allPhones[2]);
            contactsCache.add(contact);
        }
        return new Contacts(contactsCache);
    }

    public ContactsData infoFromEditForm(ContactsData contact) {
        clickOnEditContact(contact.getContactID());
        clickOn(By.xpath("//input[@value='Modify']"));
        String firstname = getElement(By.name("firstname")).getAttribute("value");
        String lastname = getElement(By.name("lastname")).getAttribute("value");
        String home = getElement(By.name("home")).getAttribute("value");
        String mobile = getElement(By.name("mobile")).getAttribute("value");
        String work = getElement(By.name("work")).getAttribute("value");
        TestBase.getDriver().navigate().back();
        TestBase.getDriver().navigate().back();
        return new ContactsData().withContactID(contact.getContactID()).withFirstname(firstname).withLastname(lastname).withHomeNumber(home).withMobileNumber(mobile).withWorkNumber(work);

    }
}
