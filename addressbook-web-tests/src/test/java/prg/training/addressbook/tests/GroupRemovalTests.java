package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class GroupRemovalTests extends TestBase {

    private boolean firstRun = true;

    @Test(dataProvider = "Group Index Provider")
    public void deleteGroup(int index) {
        appManager.getNavigationHelper().goToGroupsPage(true);
        int groupsSize = appManager.getGroupHelper().getGroups().size();
        if (firstRun == true && !appManager.getGroupHelper().isGroupPresent() || groupsSize < index) {
            for (int i = 0; i < index - groupsSize; i++) {
                String groupName = "Group " + (i + 1);
                System.out.println("No groups found  or groups size: " + groupsSize + " < " + index + " going to create group with name: " + groupName);
                appManager.getGroupHelper().createGroup(new GroupData(groupName, null, null));
            }
        }
        appManager.getGroupHelper().deleteGroup(index);
        appManager.getGroupHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Group has been removed.\n" +
                "return to the group page");
        appManager.getNavigationHelper().goToGroupsPage(false);
        //
        firstRun = false;
    }


    @DataProvider(name = "Group Index Provider")
    public static Object[][] indexProvider() {

        return new Object[][]{{1}, {3}, {5}};

    }

}
