package prg.training.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import prg.training.addressbook.base.TestBase;
import prg.training.addressbook.utils.DataModel.Contacts;
import prg.training.addressbook.utils.DataModel.ContactsData;

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
 * Created by QA Lady on 3/27/2017.
 */
public class ContactsCreationTests extends TestBase {

    @Test(dataProvider = "Valid Contacts JSON Provider")
    public void addContactsTest(ContactsData contactsData) throws Exception {

        appManager().goTo().homePage(true);

        Contacts before = appManager().contactHelper().allContacts();
        File photo = new File(getClass().getResource("/InputTestData/contact.jpg").toURI());
//        File photo = new File("addressbook-web-tests/src/test/resources/InputTestData/contact.jpg");
        contactsData.withPhoto(photo);
        appManager().contactHelper().createContact(contactsData, false);
        appManager().goTo().homePage(false);
        // хеширование  - предварительная проверка при помощи более быстрой операции
        assertThat(appManager().contactHelper().getContactsCount(), equalTo(before.size() + 1));
        Contacts after = appManager().contactHelper().allContacts();
        System.out.println("EXPECTED: " + before.withAdded(contactsData.withContactID(after.stream().mapToInt((c) -> c.getContactID()).max().getAsInt())));
        System.out.println(after);
        assertThat(after, equalTo(before.withAdded(contactsData.withContactID(after.stream().mapToInt((c) -> c.getContactID()).max().getAsInt()))));

    }

    @Test
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("addressbook-web-tests/src/test/resources/InputTestData/contact.jpg");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());

    }

    @DataProvider(name = "Valid Contacts CSV Provider")
    public Iterator<Object[]> validContactsCsv() {
        List<Object[]> list = new ArrayList<Object[]>();
        try {
//            BufferedReader reader = new BufferedReader(new FileReader(new File("addressbook-web-tests/src/test/resources/InputTestData/Contacts.csv")));
//            BufferedReader reader = new BufferedReader(new FileReader(new File(GroupCreationTests.class.getResource("/InputTestData/Contacts.csv").toURI())));
            BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getResource("/InputTestData/Contacts.csv").toURI())));
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(";");
                list.add(new Object[]{new ContactsData().withFirstname(split[0]).withLastname(split[1]).withAddress(split[2]).withHomeNumber(split[3]).withMobileNumber(split[4]).withWorkNumber(split[5]).withEmail(split[6]).withDay(split[7]).withMonth(split[8]).withYear(split[9]).withGroup(split[10])});
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.iterator();

    }

    @DataProvider(name = "Valid Contacts XML Provider")
    public Iterator<Object[]> validContactsXml() throws IOException, URISyntaxException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getResource("/InputTestData/Contacts.xml").toURI())));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(ContactsData.class);
        List<ContactsData> Contacts = (List<ContactsData>) xstream.fromXML(xml);
        return Contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();

    }

    @DataProvider(name = "Valid Contacts JSON Provider")
    public Iterator<Object[]> validContactsJson() throws IOException, URISyntaxException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(getClass().getResource("/InputTestData/Contacts.json").toURI())));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();

        List<ContactsData> Contacts = gson.fromJson(json, new TypeToken<List<ContactsData>>() {
        }.getType()); //List<ContactsData>.class
        return Contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();

    }
    
    @DataProvider(name = "ContactsInfo")
    public static Object[][] text() {

        return new Object[][]{{new ContactsData().withFirstname("firstname1").withLastname("lastname1").withAddress("address1").withHomeNumber("1").withMobileNumber("7324678090").withWorkNumber(RandomStringUtils.randomNumeric(12)).withEmail("email1@ya.ru").withGroup("Group 1").withDay("5").withMonth("December").withYear("1975")}, {new ContactsData().withFirstname("firstname2").withLastname("lastname2").withAddress("address2").withHomeNumber("2").withMobileNumber("4446632467").withWorkNumber(RandomStringUtils.randomNumeric(12)).withEmail("email2@ya.ru").withGroup("Group 2").withDay("31").withMonth("August").withYear("2005")}, {new ContactsData().withFirstname("firstname3").withLastname("lastname3").withAddress("address3").withHomeNumber("3").withMobileNumber("456797123588").withWorkNumber(RandomStringUtils.randomNumeric(12)).withEmail("email3@ya.ru").withGroup("Group 3").withDay("18").withMonth("February").withYear("1962")}};

    }
}
