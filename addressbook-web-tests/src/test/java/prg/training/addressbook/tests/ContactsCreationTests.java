package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.ContactsData;
import prg.training.addressbook.base.TestBase;

/**
 * Created by QA Lady on 3/27/2017.
 */
public class ContactsCreationTests extends TestBase {

    @Test(dataProvider = "ContactsInfo")
    public void addContactsTest(ContactsData contactsData) throws Exception {
        initContactCreation();
        completeContactsForm(contactsData);
        new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactsData.getGroupID());
        submit();
        clickOn(By.linkText("add next"));
        submit();
        goToHomePage();
    }

    //common contacts methods
    public void goToHomePage() {
        driver.findElement(By.linkText("home page")).click();
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
        driver.findElement(By.linkText("add new")).click();
    }

    protected void enterBirthdayDate(String day, String month, String year) {
        new Select(driver.findElement(By.name("bday"))).selectByVisibleText(day);
        new Select(driver.findElement(By.name("bmonth"))).selectByVisibleText(month);
        enterText(By.name("byear"), year);
    }

    @DataProvider(name = "ContactsInfo")
    public static Object[][] text() {

        return new Object[][]{{new ContactsData("firstname1", "lastname1", "address1", "1", "7324678090", "email1@ya.ru", "Group 1", "5", "December", "1975")}, {new ContactsData("firstname2", "lastname2", "address2", "2", "4446632467", "email2@ya.ru", "Group 2", "31", "August", "2005")}, {new ContactsData("firstname3", "lastname3", "address3", "3", "4446632467", "email3@ya.ru", "Group 3", "18", "February", "1967")}};

    }
}
