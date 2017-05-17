package prg.training.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.GroupData;
import prg.training.addressbook.utils.DataModel.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by QA Lady on 3/26/2017.
 */

public class GroupCreationTests extends TestBase {

    @Test(dataProvider = "Valid Groups JSON Provider")
    public void groupCreationTest(GroupData groupsData) {

        appManager().goTo().groupsPage(true);

        Groups before = appManager().groupHelper().allGroups();
//        int beforeNewGroupCreation = appManager.groupHelper().getGroupCount();

        appManager().groupHelper().createGroup(groupsData);

        Groups after = appManager().groupHelper().allGroups();
        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(before.withAdded(groupsData.withGroupID(after.stream().mapToInt((g) -> g.getGroupID()).max().getAsInt()))));
    }

    @DataProvider(name = "Valid Groups CSV Provider")
    public Iterator<Object[]> validGroupsCsv() {
        List<Object[]> list = new ArrayList<Object[]>();
        try {
//            BufferedReader reader = new BufferedReader(new FileReader(new File("addressbook-web-tests/src/test/resources/InputTestData/groups.csv")));
//            BufferedReader reader = new BufferedReader(new FileReader(new File(GroupCreationTests.class.getResource("/InputTestData/groups.csv").toURI())));
            BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getResource("/InputTestData/groups.csv").toURI())));
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(";");
                list.add(new Object[]{new GroupData().withGroupName(split[0]).withHeader(split[1]).withFooter(split[2])});
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.iterator();

    }

    @DataProvider(name = "Valid Groups XML Provider")
    public Iterator<Object[]> validGroupsXml() throws IOException, URISyntaxException {
//        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getResource("/InputTestData/groups.xml").toURI())));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
        return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
//        return list.iterator();

    }

    @DataProvider(name = "Valid Groups JSON Provider")
    public Iterator<Object[]> validGroupsJson() throws IOException, URISyntaxException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getResource("/InputTestData/groups.json").toURI())));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();

        List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
        }.getType()); //List<GroupData>.class
        return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();

    }


    @DataProvider(name = "Group Form Provider")
    public static Object[][] text() {
//when not defailt constructors were removed. using setters
        return new Object[][]{{new GroupData().withGroupName("Group 1")}, {new GroupData().withGroupName("Group 2").withHeader("header 2").withFooter("footer 2")}, {new GroupData().withGroupName("Group 3").withHeader("header 3").withFooter("footer 3")}};
//when not default constructors were available
        //        return new Object[][]{{new GroupData("Group 1", null, null)}, {new GroupData("Group 2", "header 2", "footer 2")}, {new GroupData("Group 3", "header 3", "footer 3")}};

    }


    @Test(dataProvider = "Invalid Group Form Provider")
    public void badGroupCreationTest(GroupData groupsData) {
        appManager().goTo().groupsPage(true);
        Groups before = appManager().groupHelper().allGroups();
        appManager().groupHelper().createGroup(groupsData);
        // хеширование  - предварительная проверка при помощи более быстрой операции
        assertThat(appManager().groupHelper().getGroupCount(), equalTo(before.size()));
        Groups after = appManager().groupHelper().allGroups();
        assertThat(after, equalTo(before));
    }

    @DataProvider(name = "Invalid Group Form Provider")
    public static Object[][] invalidGroupName() {
        return new Object[][]{{new GroupData().withGroupName("Group 1'")}, {new GroupData().withGroupName("Group 2'").withHeader("header 2").withFooter("footer 2")}};

    }
}
