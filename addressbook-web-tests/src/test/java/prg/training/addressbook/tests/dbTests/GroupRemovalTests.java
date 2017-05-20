package prg.training.addressbook.tests.dbTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.dataModel.GroupData;
import prg.training.addressbook.utils.dataModel.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 3/29/2017.
 */
public class GroupRemovalTests extends TestBase {

    @BeforeMethod
    private void preconditionsPrep() {
        appManager().goTo().groupsPage(true);
        int index = 5;
        //getting groups from DB
        int groupsSize = appManager().getDbHelper().groups().size();
        if (groupsSize < index) {
            for (int i = 0; i <= index - groupsSize; i++) {
                String groupName = "Group " + (i + 1);
                System.out.println("No groups found  or groups size: " + groupsSize + " < " + index + " going to create group with name: " + groupName);
                appManager().groupHelper().createGroup(new GroupData().withGroupName(groupName));
            }
        }
    }


    @Test
    public void deleteGroup() {
        //getting groups from DB
        Groups beforeGroupRemoval = appManager().getDbHelper().groups();
        GroupData deletedGroup = beforeGroupRemoval.iterator().next();
        appManager().groupHelper().deleteByIdAndCheckSuccess(deletedGroup);
        // хеширование  - предварительная проверка при помощи более быстрой операции
        assertThat(appManager().groupHelper().getGroupCount(), equalTo(beforeGroupRemoval.size() - 1));
        //getting groups from DB
        Groups afterGroupRemoval = appManager().getDbHelper().groups();
        assertThat(afterGroupRemoval, equalTo(beforeGroupRemoval.without(deletedGroup)));
        verifyGroupListInUI();
    }


    @DataProvider(name = "Group Index Provider")
    public static Object[][] indexProvider() {

        return new Object[][]{{1}, {3}, {5}};

    }

}
