package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class GroupModificationTests extends TestBase {
    @Test(dataProvider = "Group Name Provider")
    public void editGroup(int index, String name) {
        appManager.getNavigationHelper().goToGroupsPage(true);
        appManager.getGroupHelper().editGroupName(index, name);
        appManager.getGroupHelper().update();
        appManager.getGroupHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Group record has been updated.\n" +
                "return to the group page");
        appManager.getNavigationHelper().goToGroupsPage(false);

    }

    @DataProvider(name = "Group Name Provider")
    public static Object[][] nameProvider() {

        return new Object[][]{{1, "new name1"}, {3, "new name2"}, {5, "new name3"}};

    }

}
