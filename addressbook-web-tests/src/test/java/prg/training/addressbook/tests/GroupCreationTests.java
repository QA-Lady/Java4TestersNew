package prg.training.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;

/**
 * Created by QA Lady on 3/26/2017.
 */

public class GroupCreationTests extends TestBase {

    @Test(dataProvider = "Group Form Provider")
    public void groupCreationTest(GroupData groupsData) {
        appManager.getNavigationHelper().goToGroupsPage(true);
        appManager.getGroupHelper().createGroup(groupsData);
    }


    @DataProvider(name = "Group Form Provider")
    public static Object[][] text() {

        return new Object[][]{{new GroupData("Group 1", null, null)}, {new GroupData("Group 2", "header 2", "footer 2")}, {new GroupData("Group 3", "header 3", "footer 3")}};

    }

}
