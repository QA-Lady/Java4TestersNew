package prg.training.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import prg.training.rest.base.TestBase;

import java.io.IOException;
import java.util.Set;


public class BugifyRestAssuredTests extends TestBase {

    @BeforeClass
    public void init() {
        RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
    }


    @Test
    public void printClosedIssueTest() throws IOException {
        try {
            Set<Issue> closedIssues = getIssues();
            Issue issue = closedIssues.iterator().next();
            //Will skip entire test if the issue is not closed
            skipIfNotFixed(issue.getId());
            System.out.println("Closed Issue with ID: " + issue.getId() + " and subject: " + issue.getSubject());
        } catch (SkipException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Set<Issue> getIssues() throws IOException {
        String json = RestAssured.get("http://demo.bugify.com/api/issues.json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }


}
