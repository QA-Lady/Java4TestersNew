package prg.training.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;
import prg.training.addressbook.utils.DataModel.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 3/26/2017.
 */

public class GroupCreationTests extends TestBase {

    @Test(dataProvider = "Group Form Provider")
    public void groupCreationTest(GroupData groupsData) {

        appManager().goTo().groupsPage(true);

        Groups before = appManager().groupHelper().allGroups();
//        int beforeNewGroupCreation = appManager.groupHelper().getGroupCount();

        appManager().groupHelper().createGroup(groupsData);

        Groups after = appManager().groupHelper().allGroups();
        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(before.withAdded(groupsData.withGroupID(after.stream().mapToInt((g) -> g.getGroupID()).max().getAsInt()))));
    }


    @DataProvider(name = "Group Form Provider")
    public static Object[][] text() {
//when not defailt constructors were removed. using setters
        return new Object[][]{{new GroupData().withGroupName("Group 1")}, {new GroupData().withGroupName("Group 2").withHeader("header 2").withFooter("footer 2")}, {new GroupData().withGroupName("Group 3").withHeader("header 3").withFooter("footer 3")}};
//when not default constructors were available
        //        return new Object[][]{{new GroupData("Group 1", null, null)}, {new GroupData("Group 2", "header 2", "footer 2")}, {new GroupData("Group 3", "header 3", "footer 3")}};

    }

}
