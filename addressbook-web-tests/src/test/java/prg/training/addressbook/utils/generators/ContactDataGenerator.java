package prg.training.addressbook.utils.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.RandomStringUtils;
import prg.training.addressbook.utils.DataModel.ContactsData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QA Lady on 5/15/2017.
 */
public class ContactDataGenerator {

    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

/*
Program Arguments: -c 3 -f src\test\resources\InputTestData\contacts.json -d json
            Working dir: C:\Work\java4testers_new\addressbook-web-tests
            */

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            jCommander.usage();
            return;
        }
        generator.run();


    }

    private void run() throws IOException {

        List<ContactsData> contacts = generatecontacts(count);
        if (format.equals("csv")) {
            saveAsCsv(contacts, new File(file));
        } else if (format.equals("xml")) {
            saveAsXml(contacts, new File(file));
        } else if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Unrecognized format " + format);
        }

    }

    private void saveAsJson(List<ContactsData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();

    }

    private void saveAsXml(List<ContactsData> contacts, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.alias("contact", ContactsData.class);
        xStream.processAnnotations(ContactsData.class);
        String xml = xStream.toXML(contacts);
        Writer writer = new FileWriter(file);
        writer.write(xml);
        writer.close();

    }

    private void saveAsCsv(List<ContactsData> contacts, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        Writer writer = new FileWriter(file);
        for (ContactsData contact : contacts) {
            writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getLastname(), contact.getAddress(), contact.getHomeNumber(), contact.getMobileNumber(), contact.getWorkNumber(), contact.getEmail(), contact.getDay(), contact.getMonth(), contact.getYear(), contact.getGroup()));
        }
        writer.close();
    }

    private List<ContactsData> generatecontacts(int count) {
        List<ContactsData> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int cnt_count = i + 1;
            contacts.add(new ContactsData().withFirstname(String.format("firstname %s", cnt_count)).withLastname(String.format("lastname %s", cnt_count)).withAddress(String.format("address %s", cnt_count)).withHomeNumber(RandomStringUtils.randomNumeric(7)).withMobileNumber(RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(3) + " " + RandomStringUtils.randomNumeric(4)).withWorkNumber(RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(4)).withEmail(RandomStringUtils.randomAlphanumeric(5) + "@" + RandomStringUtils.randomNumeric(4) + ".com").withDay(RandomStringUtils.randomNumeric(1)).withMonth("February").withYear("1975").withGroup(String.format("Group %s", cnt_count)));
        }
        return contacts;
    }

}
