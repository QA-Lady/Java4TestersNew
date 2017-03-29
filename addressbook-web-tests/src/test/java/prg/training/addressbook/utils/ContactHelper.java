package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.utils.DataModel.ContactsData;
import prg.training.addressbook.utils.appManager.AppManager;

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

    public void selectGroup4Contact(ContactsData contactsData) {
        new Select(getElement(By.name("new_group"))).selectByVisibleText(contactsData.getGroupID());
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
        clickOn(By.xpath("//tr[@name='entry'][" + (index + 1) + "]" + "//input[@name='selected[]']"));
        //invoke Contact Edit
        clickOn(By.xpath("//tr[@name='entry'][" + (index + 1) + "]" + "/td[7]/a/img"));
        WebElement modifyBtn = getElement(By.xpath("//input[@value='Modify']"));
        clickOn(modifyBtn);
        enterText(By.name("firstname"), name);

    }

}
