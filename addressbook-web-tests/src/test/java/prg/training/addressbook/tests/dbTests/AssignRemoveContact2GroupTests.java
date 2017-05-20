package prg.training.addressbook.tests.dbTests;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import prg.training.addressbook.base.HelperBase;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.dataModel.Contacts;
import prg.training.addressbook.utils.dataModel.ContactsData;
import prg.training.addressbook.utils.dataModel.GroupData;
import prg.training.addressbook.utils.dataModel.Groups;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 5/19/2017.
 */
public class AssignRemoveContact2GroupTests extends TestBase {
    Logger log = LoggerFactory.getLogger(AssignRemoveContact2GroupTests.class);

    @BeforeMethod
    private void preconditionsPrep() {
        appManager().goTo().homePage(true);
        int index = 5;
        //get contacts from DB
//        int contactsSize = appManager().getDbHelper().contacts().size();
//        if (contactsSize < index) {
//            for (int i = 0; i <= index - contactsSize; i++) {
//                String firstname = "firstname" + (i + 1);
//                System.out.println("No contacts found or contacts size: " + contactsSize + " < " + index + " going to create contact with name: " + firstname);
//                appManager().contactHelper().createContact(new ContactsData().withFirstname(firstname).withLastname("lastname" + (i + 1)).withAddress("address1").withHomeNumber(RandomStringUtils.randomNumeric(7)).withMobileNumber(RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(4)).withWorkNumber(RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(4)).withEmail(RandomStringUtils.randomAlphanumeric(5) + "@" + RandomStringUtils.randomNumeric(4) + ".com").withDay("5").withMonth("December").withYear("1975"), true);
//            }
//        }
        //need to make sure there are groups we can add our Contact(s) To and remove From ;)
        appManager().goTo().groupsPage(true);
        //getting groups from DB
        int groupsSize = appManager().getDbHelper().groups().size();
        if (groupsSize < index) {
            for (int i = 0; i <= index - groupsSize; i++) {
                String groupName = "Group " + (i + 1);
                System.out.println("No groups found  or groups size: " + groupsSize + " < " + index + " going to create group with name: " + groupName);
                appManager().groupHelper().createGroup(new GroupData().withGroupName(groupName));
            }
        }
        //return to contact page
        appManager().goTo().homePage(true);
    }


    @Test
    public void addContact2Group() {

        //getting contacts from DB
        Contacts before = appManager().getDbHelper().contacts();
        //select contact to modify
        ContactsData contact2Modify = before.iterator().next();
        Groups contactsGroupsBefore = contact2Modify.getGroups();
        Groups availableGroups = appManager().getDbHelper().groups();
        if (contactsGroupsBefore.equals(availableGroups)) {
            log.warn("Contact assigned to all available groups need to create a new group");
            appManager().goTo().groupsPage(true);
            String groupName = RandomStringUtils.randomAlphanumeric(6);
            log.info("Creating a group with name: " + groupName);
            appManager().groupHelper().createGroup(new GroupData().withGroupName(groupName));
            //update available groups
            availableGroups = appManager().getDbHelper().groups();
            //return to contact page
            appManager().goTo().homePage(true);
        }
        //show all contacts
        showOnlyContactsAssignedTo("[all]");
        GroupData group = assignGroup2Contact(contact2Modify, contactsGroupsBefore, availableGroups);
        assertThat(contact2Modify.getGroups(), CoreMatchers.equalTo(contactsGroupsBefore.withAdded(group)));
    }

    public GroupData assignGroup2Contact(ContactsData contact2Modify, Groups contactsGroupsBefore, Groups availableGroups) {
        //select contact to modify
        appManager().contactHelper().selectContactById(contact2Modify);
        // get unique group that contact does not have yet to be added to contact in the next step
        GroupData group = availableGroups.getUnique(contactsGroupsBefore).iterator().next();
        //add it to groups in ContactData for use
        contact2Modify.inGroup(group);
        //select the group in pulldown
        new Select(new HelperBase(appManager()).getElement(By.name("to_group"))).selectByVisibleText(group.getGroupName());
        new HelperBase(appManager()).clickOn(By.name("add"));
        return group;
    }

    public void showOnlyContactsAssignedTo(String group2FilterBy) {

        new Select(new HelperBase(appManager()).getElement(By.name("group"))).selectByVisibleText(group2FilterBy);
    }


    @Test
    public void rmContactFromGroup() {
        //getting contacts from DB
        Contacts before = appManager().getDbHelper().contacts();
        //get contact with groups to modify
        ContactsData contact2Modify = null;
        Groups contactsGroupsBefore = null;
        for (ContactsData aBefore : before) {
            contact2Modify = aBefore;
            contactsGroupsBefore = contact2Modify.getGroups();
            if (!contactsGroupsBefore.isEmpty()) {
                break;
            }
        }
        GroupData group2Remove;
        if (contactsGroupsBefore != null && !contactsGroupsBefore.isEmpty()) {
            group2Remove = contactsGroupsBefore.iterator().next();
        } else {
            // No contacts with assigned groups found. Assign one
            Groups availableGroups = appManager().getDbHelper().groups();
            contactsGroupsBefore = new Groups();
            group2Remove = assignGroup2Contact(contact2Modify, contactsGroupsBefore, availableGroups);
            contactsGroupsBefore.withAdded(group2Remove);
        }
        //filter by selected group2Remove that is assigned to the contact we are modifying
        showOnlyContactsAssignedTo(group2Remove.getGroupName());
        //select contact to modify
        appManager().contactHelper().selectContactById(contact2Modify);
        new HelperBase(appManager()).clickOn(By.name("remove"));
        Groups groups = contact2Modify.getGroups();
        groups.remove(group2Remove);
        assertThat(groups, CoreMatchers.equalTo(contactsGroupsBefore.without(group2Remove)));
    }

}