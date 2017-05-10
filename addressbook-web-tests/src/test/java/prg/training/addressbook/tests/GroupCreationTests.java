package prg.training.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by QA Lady on 3/26/2017.
 */

public class GroupCreationTests extends TestBase {

    @Test(dataProvider = "Group Form Provider")
    public void groupCreationTest(GroupData groupsData) {

        appManager.getNavigationHelper().goToGroupsPage(true);

        List<GroupData> before = appManager.getGroupHelper().getGroupList();
//        int beforeNewGroupCreation = appManager.getGroupHelper().getGroupCount();

        appManager.getGroupHelper().createGroup(groupsData);

//        int afterGroupCreation = appManager.getGroupHelper().getGroupCount();
//        Assert.assertEquals(afterGroupCreation, beforeNewGroupCreation+1, "check that 1 group was created and added to the list");
        List<GroupData> after = appManager.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1, "check that 1 group was created and added to the list");

        //available starting from java 8
        groupsData.setGroupID(after.stream().max((o1, o2) -> Integer.compare(o1.getGroupID(), o2.getGroupID())).get().getGroupID());
        before.add(groupsData);
        //sorting approach before java 8
//        Collections.sort(before);
//        Collections.sort(after);

        //sorting approach starting from java 8
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getGroupID(), g2.getGroupID());
        before.sort(byId);
        after.sort(byId);

        Assert.assertEquals(before, after);
    }


    @DataProvider(name = "Group Form Provider")
    public static Object[][] text() {

        return new Object[][]{{new GroupData("Group 1", null, null)}, {new GroupData("Group 2", "header 2", "footer 2")}, {new GroupData("Group 3", "header 3", "footer 3")}};

    }

}
