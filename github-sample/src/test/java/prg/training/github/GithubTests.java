package prg.training.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

    @Test
    public void testCommits() throws IOException {
        Github github = new RtGithub("7bbcbec0f36dc33b69380e2e04043c47b0f282f4");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("QA-Lady", "Java4TestersNew")).commits();
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}
