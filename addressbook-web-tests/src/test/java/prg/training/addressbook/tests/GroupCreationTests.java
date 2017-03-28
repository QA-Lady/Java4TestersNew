package prg.training.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.GroupData;
import prg.training.addressbook.base.TestBase;

/**
 * Created by QA Lady on 3/26/2017.
 */

public class GroupCreationTests extends TestBase {

    @Test(dataProvider = "Group Form Provider")
    public void groupCreationTest(GroupData groupsData) {
        goToGroupsPage();
        initGroupCreation();
        completeGroupForm(groupsData);
        submit();
        returnToGroupPage();
    }

    public void returnToGroupPage() {
        clickOn(By.linkText("group page"));
    }

    public void completeGroupForm(GroupData groupData) {
        enterText(By.name("group_name"), groupData.getGroupName());
        enterText(By.name("group_header"), groupData.getHeader());
        enterText(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        clickOn(By.name("new"));
    }

    public void goToGroupsPage() {
        clickOn(By.linkText("groups"));
    }

    @DataProvider(name = "Group Form Provider")
    public static Object[][] text() {

        return new Object[][]{{new GroupData("Group 1", "header 1", "footer 1")}, {new GroupData("Group 2", "header 2", "footer 2")}, {new GroupData("Group 3", "header 3", "footer 3")}};

    }

}
