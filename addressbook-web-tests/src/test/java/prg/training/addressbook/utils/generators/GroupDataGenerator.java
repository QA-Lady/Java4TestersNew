package prg.training.addressbook.utils.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import prg.training.addressbook.utils.dataModel.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QA Lady on 5/15/2017.
 */
public class GroupDataGenerator {

    @Parameter(names = "-c", description = "Group count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;


    public static void main(String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            jCommander.usage();
            return;
        }
        generator.run();


    }

    /*to run from commandline:
                -c 3 -f src\test\resources\InputTestData\groups.json -d json*/
    private void run() throws IOException {

        List<GroupData> groups = generateGroups(count);
        if (format.equals("csv")) {
            saveAsCsv(groups, new File(file));
        } else if (format.equals("xml")) {
            saveAsXml(groups, new File(file));
        } else if (format.equals("json")) {
            saveAsJson(groups, new File(file));
        } else {
            System.out.println("Unrecognized format " + format);
        }

    }

    private void saveAsJson(List<GroupData> groups, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(groups);
        //startging from java 7 new try() that automatically closes writer is available
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
            //it will be automatically closed in this try block
//            writer.close();
        }

    }

    private void saveAsXml(List<GroupData> groups, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.alias("group", GroupData.class);
        xStream.processAnnotations(GroupData.class);
        String xml = xStream.toXML(groups);
        //startging from java 7 new try() that automatically closes writer is available
        try (Writer writer = new FileWriter(file)) {
            writer.write(xml);
            //it will be automatically closed in this try block
//            writer.close();
        }

    }

    private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
        System.out.println(new File(".").getAbsolutePath());
        //startging from java 7 new try() that automatically closes writer is available
        try (Writer writer = new FileWriter(file)) {
        for (GroupData group : groups) {
            writer.write(String.format("%s;%s;%s\n", group.getGroupName(), group.getHeader(), group.getFooter()));
        }
            //it will be automatically closed in this try block
//            writer.close();
        }
    }

    private List<GroupData> generateGroups(int count) {
        List<GroupData> groups = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int gr_count = i + 1;
            groups.add(new GroupData().withGroupName(String.format("group %s", gr_count)).withHeader(String.format("header %s", gr_count)).withFooter(String.format("footer %s", gr_count)));
        }
        return groups;
    }

}
