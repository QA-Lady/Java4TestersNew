package prg.training.rest.base;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;

/**
 * Created by QA Lady on 5/21/2017.
 */
public abstract class TestBase {


    private boolean isIssueOpen(int issueId) {
        String issueStatus = getIssueById(issueId).get("state_name").getAsString();
        return !(issueStatus.equals("Closed") || issueStatus.equals("Resolved"));
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("The test is skipped. Issue with ID: " + issueId + " is opened");
        }
    }


    public JsonObject getIssueById(int issueId) {
        String json = RestAssured.get(String.format("http://demo.bugify.com/api/issues/%s.json", issueId)).asString();
        JsonObject jsonObject = ((new JsonParser().parse(json).getAsJsonObject()).get("issues")).getAsJsonArray().get(0).getAsJsonObject();
        return jsonObject;
    }


}
