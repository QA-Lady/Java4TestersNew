package prg.training.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;
import prg.training.addressbook.utils.DataModel.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class GroupModificationTests extends TestBase {

    @BeforeMethod
    private void preconditionsPrep() {
        appManager().goTo().groupsPage(true);
        int index = 5;
        int groupsSize = appManager().groupHelper().getGroups().size();
        if (groupsSize < index) {
            for (int i = 0; i <= index - groupsSize; i++) {
                String groupName = "Group " + (i + 1);
                System.out.println("No groups found  or groups size: " + groupsSize + " < " + index + " going to create group with name: " + groupName);
                appManager().groupHelper().createGroup(new GroupData().withGroupName(groupName));
            }
        }

    }

    @Test(dataProvider = "Group Name Provider")
    public void editGroupTest(String name) {

        Groups beforeGroupEdit = appManager().groupHelper().allGroups();
        GroupData modifiedGroup = beforeGroupEdit.iterator().next();
        GroupData group = new GroupData().withGroupID(modifiedGroup.getGroupID()).withGroupName(name).withHeader("header").withFooter("footer");
        appManager().groupHelper().editAndCheckSuccess(group);

        Groups afterGroupEdit = appManager().groupHelper().allGroups();
        assertThat(beforeGroupEdit.size(), equalTo(afterGroupEdit.size()));
        assertThat(afterGroupEdit, equalTo(beforeGroupEdit.without(modifiedGroup).withAdded(group)));
    }


    @DataProvider(name = "Group Name Provider")
    public static Object[][] nameProvider() {

        return new Object[][]{{"new name1"}, {"new name2"}, {"new name3"}};

    }

}
