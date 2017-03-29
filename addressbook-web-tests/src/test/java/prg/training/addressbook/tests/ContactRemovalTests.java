package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class ContactRemovalTests extends TestBase {

    @Test(dataProvider = "Contact Index Provider")
    public void deleteContact(int index) {
        appManager.getContactHelper().deleteContact(index);
        appManager.getContactHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Record successful deleted");
        appManager.getNavigationHelper().goToHomePage(true);
    }

    @DataProvider(name = "Contact Index Provider")
    public static Object[][] indexProvider() {

        return new Object[][]{{2}, {3}, {5}};

    }

}
