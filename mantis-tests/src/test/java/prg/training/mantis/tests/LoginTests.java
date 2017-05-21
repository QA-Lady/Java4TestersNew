package prg.training.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import prg.training.mantis.tests.base.TestBase;
import prg.training.mantis.utils.HttpSession;

import java.io.IOException;

/**
 * Created by QA Lady on 5/20/2017.
 */
public class LoginTests extends TestBase {

    @Test
    public void testLogin() throws IOException {
        HttpSession session = appManager().newSession();
        Assert.assertTrue(session.login("administrator", "root"));
        Assert.assertTrue(session.isLoggedInAs("administrator"));
    }
}
