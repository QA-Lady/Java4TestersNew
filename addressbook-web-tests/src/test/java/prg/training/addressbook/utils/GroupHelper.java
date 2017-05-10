package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;
import prg.training.addressbook.utils.appManager.AppManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QA Lady on 3/28/2017.
 */
public class GroupHelper extends HelperBase {


    public GroupHelper(AppManager appManager) {
        super(appManager);
    }

    public void completeGroupForm(GroupData groupData) {
        enterText(By.name("group_name"), groupData.getGroupName());
        enterText(By.name("group_header"), groupData.getHeader());
        enterText(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        clickOn(By.name("new"));
    }

    public void deleteGroup(int index) {
        clickOn(By.xpath("//span[@class='group'][" + (index) + "]/input[@name='selected[]'][1]"));
        clickOn(By.name("delete"));
    }


    public void editGroupName(int index, String name) {
        //select by index using xpath approach
        clickOn(By.xpath("//span[@class='group'][" + (index) + "]/input[@name='selected[]'][1]"));

        //select by index from group list
//        TestBase.getDriver().findElements(By.name("selected[]")).get(index).click();

        clickOn(By.name("edit"));
        enterText(By.name("group_name"), name);
    }

    public void createGroup(GroupData groupsData) {
        initGroupCreation();
        completeGroupForm(groupsData);
        submit();
        appManager.getNavigationHelper().goToGroupsPage(false);
    }

    public boolean isGroupPresent() {
        return isElementPresent(TestBase.getDriver(), By.xpath("//input[@name='selected[]'][1]"));
    }

    public List<WebElement> getGroups() {
        List<WebElement> groups = TestBase.getDriver().findElements(By.xpath("//input[@name='selected[]'][1]"));
        return groups;
    }

    public int getGroupCount() {
        return TestBase.getDriver().findElements(By.xpath("//input[@name='selected[]']")).size();
    }

    public List<GroupData> getGroupList() {
        List<GroupData> groups = new ArrayList<GroupData>();
        List<WebElement> elements = TestBase.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("span.group")));
        for (WebElement element : elements) {
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            GroupData group = new GroupData(name, id, null, null);
            groups.add(group);
        }
        return groups;
    }
}
