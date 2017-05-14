package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;
import prg.training.addressbook.utils.DataModel.Groups;
import prg.training.addressbook.utils.appManager.AppManager;

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

    public void deleteGroupById(int id) {
        clickOn(By.xpath("//input[@value='" + id + "']"));
        clickOn(By.name("delete"));
    }

    public void editGroup(GroupData group) {
        clickOn(By.xpath("//input[@value='" + group.getGroupID() + "']"));
        clickOn(By.name("edit"));
        completeGroupForm(group);
    }

    public void createGroup(GroupData groupsData) {
        initGroupCreation();
        completeGroupForm(groupsData);
        submit();
        appManager.goTo().groupsPage(false);
    }

    public void editAndCheckSuccess(GroupData groupToEdit) {
        int groupID = groupToEdit.getGroupID();
        System.out.println("Going to modify group with index: " + groupID);
        editGroup(groupToEdit);
        update();
        checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Group record has been updated.\n" +
                "return to the group page");
        appManager.goTo().groupsPage(false);
    }

    public void deleteByIdAndCheckSuccess(GroupData groupToDelete) {
        deleteGroupById(groupToDelete.getGroupID());
        checkSuccessMessage(By.xpath("//div[@class='msgbox']"), "Group has been removed.\n" +
                "return to the group page");
        appManager.goTo().groupsPage(false);
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

    public Groups allGroups() {
        Groups groups = new Groups();
        try {
            List<WebElement> elements = TestBase.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("span.group")));
            for (WebElement element : elements) {
                String name = element.getText();
                int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
                groups.add(new GroupData().withGroupName(name).withGroupID(id));
            }
        } catch (Exception e) {
            System.out.println("No Groups found on page");
            ;
        }
        return groups;
    }


}
