package prg.training.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import prg.training.mantis.model.Issue;
import prg.training.mantis.model.Project;
import prg.training.mantis.tests.base.TestBase;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;


/**
 * Created by QA Lady on 5/21/2017.
 */
public class SoapTests extends TestBase {


    @Test
    public void getProjectsTest() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = appManager().soapHelper().getProjects();
        System.out.println("Number of Projects: " + projects.size());
        for (Project project : projects) {
            System.out.println("Project: " + project.getName());
        }
    }

    @Test
    public void createIssueTest() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = appManager().soapHelper().getProjects();
        Issue issue = new Issue().withSummary("Test issue")
                .withDescription("Test issue description").withProject(projects.iterator().next());
        Issue created = appManager().soapHelper().addIssue(issue);
        Assert.assertEquals(issue.getSummary(), created.getSummary());
    }
}
