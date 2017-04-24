package prg.training.addressbook.utils;

import org.openqa.selenium.By;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.utils.DataModel.GroupData;
import prg.training.addressbook.utils.appManager.AppManager;

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
        clickOn(By.xpath("//span[@class='group'][" + (index) + "]/input[@name='selected[]'][1]"));
        clickOn(By.name("edit"));
        enterText(By.name("group_name"), name);
    }


}