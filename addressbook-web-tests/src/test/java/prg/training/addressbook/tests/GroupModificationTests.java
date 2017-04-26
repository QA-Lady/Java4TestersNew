package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class GroupModificationTests extends TestBase {

    private boolean firstRun = true;

    @Test(dataProvider = "Group Name Provider")
    public void editGroup(int index, String name) {
        appManager.getNavigationHelper().goToGroupsPage(true);
        int groupsSize = appManager.getGroupHelper().getGroups().size();
        if (firstRun == true && !appManager.getGroupHelper().isGroupPresent() || groupsSize < index) {
            for (int i = 0; i < index - groupsSize; i++) {
                String groupName = "Group " + (i + 1);
                System.out.println("No groups found  or groups size: " + groupsSize + " < " + index + " going to create group with name: " + groupName);
                appManager.getGroupHelper().createGroup(new GroupData(groupName, null, null));
            }
        }
        System.out.println("Going to modify group with index: " + index);
        appManager.getGroupHelper().editGroupName(index, name);
        appManager.getGroupHelper().update();
        appManager.getGroupHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Group record has been updated.\n" +
                "return to the group page");
        appManager.getNavigationHelper().goToGroupsPage(false);
        //
        firstRun = false;
    }

    @DataProvider(name = "Group Name Provider")
    public static Object[][] nameProvider() {

        return new Object[][]{{1, "new name1"}, {3, "new name2"}, {5, "new name3"}};

    }

}
