package prg.training.mantis.tests;

import org.testng.annotations.Test;
import prg.training.mantis.model.IssuesData;
import prg.training.mantis.tests.base.TestBase;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

/**
 * Created by QA Lady on 5/21/2017.
 */
public class ClosedIssuesTest extends TestBase {

    @Test
    public void testPrintClosedIssuesOnly() throws RemoteException, ServiceException, MalformedURLException {
        for (IssuesData i : appManager().dbHelper().issues()) {
            if (!isIssueOpen(i.getId())) {
                System.out.println(i.getId() + i.getSummary());
            } else {
                skipIfNotFixed(i.getId());
            }
        }
    }

}
