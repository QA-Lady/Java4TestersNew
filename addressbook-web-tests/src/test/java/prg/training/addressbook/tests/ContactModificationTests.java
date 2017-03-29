package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class ContactModificationTests extends TestBase {
    @Test(dataProvider = "Contact Name Provider")
    public void editContact(int index, String name) {
        appManager.getContactHelper().editContactName(index, name);
        appManager.getContactHelper().update();
        appManager.getContactHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Address book updated\nreturn to home page");
        appManager.getNavigationHelper().goToHomePage(false);

    }

    @DataProvider(name = "Contact Name Provider")
    public static Object[][] nameProvider() {

        return new Object[][]{{3, "new name1"}, {5, "new name2"}, {8, "new name3"}};
    }


}
