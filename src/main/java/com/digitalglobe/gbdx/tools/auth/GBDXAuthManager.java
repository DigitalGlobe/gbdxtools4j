package com.digitalglobe.gbdx.tools.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.digitalglobe.gbdx.tools.config.ConfigurationManager;
import com.google.gson.Gson;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class to use the authentication credentials and to authenticate
 * against the GBDX authentication service.
 */
public class GBDXAuthManager {
    private static final Logger log = LoggerFactory.getLogger(GBDXAuthManager.class);
    private String accessToken = null;


    /**
     * Construct the auth manager and get the access token needed to make calls to the GBDX system.
     *
     */
    public GBDXAuthManager() {
        ConfigurationManager configurationManager = new ConfigurationManager();

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(configurationManager.getAuthUrl());

            List<BasicNameValuePair> parametersBody = new ArrayList<>();
            parametersBody.add(new BasicNameValuePair("grant_type", "password"));
            parametersBody.add(new BasicNameValuePair("username", configurationManager.getUserName()));
            parametersBody.add(new BasicNameValuePair("password", configurationManager.getPassword()));
            post.setEntity(new UrlEncodedFormEntity(parametersBody));

            post.addHeader("Authorization",
                    "Basic " + Base64.getEncoder().encodeToString((configurationManager.getClientId()
                             + ":" + configurationManager.getClientSecret()).getBytes()));

            try( CloseableHttpResponse response = httpclient.execute(post)) {
                int httpResponseCode = response.getStatusLine().getStatusCode();

                if( httpResponseCode == 200 ) {
                    Gson gson = new Gson();
                    OAuth2Token oAuth2Token = gson.fromJson(EntityUtils.toString(response.getEntity()), OAuth2Token.class );
                    accessToken = oAuth2Token.getAccessToken();
                }
            }
        }
        catch (IOException ioe) {
            log.error("error getting token: ", ioe);
        }
    }

    /**
     * Get the access token as returned by the auth system
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }
}
