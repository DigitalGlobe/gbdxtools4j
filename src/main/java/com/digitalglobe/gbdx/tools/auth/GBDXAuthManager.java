package com.digitalglobe.gbdx.tools.auth;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.digitalglobe.gbdx.tools.config.ConfigurationManager;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
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
 * against the GBDX authentication service.  The "backing store" for
 * the token is the config service.
 */
public class GBDXAuthManager {
    private static final Logger log = LoggerFactory.getLogger(GBDXAuthManager.class);

    private static final Object lock = new Object();
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private ConfigurationManager configurationManager;


    /**
     * Construct the auth manager and get the access token needed to make calls to the GBDX system.
     *
     */
    public GBDXAuthManager() {
        synchronized (lock) {
            if( initialized.get() )
                return;

            configurationManager = new ConfigurationManager();

            if (configurationManager.getAccessToken() == null)
                getNewToken();
            else {
                ZonedDateTime tokenExpiration = configurationManager.getAccessTokenExpiration();
                ZonedDateTime now = ZonedDateTime.now();

                //
                // do we have at least an hour left on the token?
                //
                if( now.plusSeconds(3600).compareTo(tokenExpiration) > 1 )
                    getNewToken();
            }

            initialized.set(true);
        }
    }

    private void getNewToken() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(configurationManager.getAuthUrl());

            List<BasicNameValuePair> parametersBody = new ArrayList<>();
            parametersBody.add(new BasicNameValuePair("grant_type", "password"));
            parametersBody.add(new BasicNameValuePair("username", configurationManager.getUserName()));
            parametersBody.add(new BasicNameValuePair("password", configurationManager.getPassword()));
            post.setEntity(new UrlEncodedFormEntity(parametersBody));

            post.addHeader("Authorization",
                    "Basic " + Base64.encodeBase64String((configurationManager.getClientId()
                             + ":" + configurationManager.getClientSecret()).getBytes()));

            try( CloseableHttpResponse response = httpclient.execute(post)) {
                int httpResponseCode = response.getStatusLine().getStatusCode();

                if( httpResponseCode == 200 ) {
                    Gson gson = new Gson();
                    OAuth2Token oAuth2Token = gson.fromJson(EntityUtils.toString(response.getEntity()), OAuth2Token.class );

                    // log.debug( "oauth token is " + oAuth2Token.toString() );

                    configurationManager.setAccessToken( oAuth2Token.getAccessToken() );

                    ZonedDateTime tokenExpiration = ZonedDateTime.now().plusSeconds(oAuth2Token.getExpiresIn());
                    configurationManager.setAccessTokenExpiration(tokenExpiration);

                    configurationManager.saveUpdatedParameters();
                }
            }
        }
        catch (IOException ioe) {
            log.error("error getting token: ", ioe);
        }
    }
}
