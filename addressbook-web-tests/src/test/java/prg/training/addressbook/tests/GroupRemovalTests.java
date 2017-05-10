package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;

import java.util.Comparator;
import java.util.List;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class GroupRemovalTests extends TestBase {


    @Test(dataProvider = "Group Index Provider")
    public void deleteGroup(int index) {
        appManager.getNavigationHelper().goToGroupsPage(true);
        int groupsSize = appManager.getGroupHelper().getGroups().size();
        if (!appManager.getGroupHelper().isGroupPresent() || groupsSize < index) {
            for (int i = 0; i <= index - groupsSize; i++) {
                String groupName = "Group " + (i + 1);
                System.out.println("No groups found  or groups size: " + groupsSize + " < " + index + " going to create group with name: " + groupName);
                appManager.getGroupHelper().createGroup(new GroupData(groupName, null, null));
            }
        }
        List<GroupData> beforeGroupRemoval = appManager.getGroupHelper().getGroupList();

        appManager.getGroupHelper().deleteGroup(index);
        appManager.getGroupHelper().checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Group has been removed.\n" +
                "return to the group page");
        appManager.getNavigationHelper().goToGroupsPage(false);
        //
        List<GroupData> afterGroupRemoval = appManager.getGroupHelper().getGroupList();
        Assert.assertEquals(afterGroupRemoval.size(), beforeGroupRemoval.size() - 1, "check that 1 group was really deleted from the list");
        System.out.println("Before: " + beforeGroupRemoval);
        System.out.println("After: " + afterGroupRemoval);

        //removed deleted group from beforeGroup removal test to compare the remaining groups in before and after lists
        beforeGroupRemoval.remove(index - 1/*xpath starts index from 1 and array list starts from 0*/);

        //sorting approach starting from java 8
        Comparator<? super GroupData> byId = (GroupData g1, GroupData g2) -> Integer.compare(g1.getGroupID(), g2.getGroupID());
        beforeGroupRemoval.sort(byId);
        afterGroupRemoval.sort(byId);
        System.out.println("Before: " + beforeGroupRemoval);
        System.out.println("After: " + afterGroupRemoval);
        Assert.assertEquals(afterGroupRemoval, beforeGroupRemoval);
    }


    @DataProvider(name = "Group Index Provider")
    public static Object[][] indexProvider() {

        return new Object[][]{{1}, {3}, {5}};

    }

}
