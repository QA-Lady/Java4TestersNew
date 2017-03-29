package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class GroupRemovalTests extends TestBase {

    @Test(dataProvider = "Group Index Provider")
    public void deleteGroup(int index) {
        appManager.getNavigationHelper().goToGroupsPage(true);
        appManager.getGroupHelper().deleteGroup(index);
        appManager.getGroupHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Group has been removed.\n" +
                "return to the group page");
        appManager.getNavigationHelper().goToGroupsPage(false);

    }


    @DataProvider(name = "Group Index Provider")
    public static Object[][] indexProvider() {

        return new Object[][]{{1}, {3}, {5}};

    }

}
