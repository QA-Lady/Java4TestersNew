package prg.training.mantis.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import prg.training.mantis.model.UserData;
import prg.training.mantis.utils.appmanager.AppManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QA Lady on 5/20/2017.
 */
public class HttpSession {

    private CloseableHttpClient httpclient;
    private AppManager appManager;

    public HttpSession(AppManager appManager) {
        this.appManager = appManager;
        httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();//LaxRedirectStrategy will automatically handle redirects
    }

    public boolean login(UserData user) throws IOException {
        return login(user.getUsername(), user.getPassword());
    }

    public boolean login(String username, String password) throws IOException {
        //request has type post (initialized empty request in the begining
        HttpPost post = new HttpPost(appManager.getProperty("web.baseUrl") + "/login.php");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("secure_session", "on"));
        params.add(new BasicNameValuePair("return", "index.php"));
        //add params to post
        post.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = httpclient.execute(post);
        String body = geTextFrom(response);
        return body.contains(String.format("<span class=\"italic\">%s</span>", username));
    }


    private String geTextFrom(CloseableHttpResponse response) throws IOException {
        try {
            return EntityUtils.toString(response.getEntity());
        } finally {
            response.close();
        }
    }

    public boolean isLoggedInAs(String username) throws IOException {
        //get type of request
        HttpGet get = new HttpGet(appManager.getProperty("web.baseUrl") + "/index.php");
        CloseableHttpResponse response = httpclient.execute(get);
        String body = geTextFrom(response);
        return body.contains(String.format("<span class=\"italic\">%s</span>", username));
    }


}
