package com.digitalglobe.gbdx.tools.auth;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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
 * against the GBDX authentication service.
 */
public class GBDXAuthManager {
    private static final Logger log = LoggerFactory.getLogger(GBDXAuthManager.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private static String accessToken = null;
    private static long tokenExpiration;
    private static final Object lock = new Object();
    private static final AtomicBoolean initialized = new AtomicBoolean(false);




    /**
     * Construct the auth manager and get the access token needed to make calls to the GBDX system.
     *
     */
    public GBDXAuthManager() {
        synchronized (lock) {
            if( initialized.get() )
                return;

            ConfigurationManager configurationManager = new ConfigurationManager();

            if (configurationManager.getAccessToken() != null) {
                String tokenExpirationString = configurationManager.getTokenExpiration();

                try {
                    Date tokenExpirationDate = sdf.parse(tokenExpirationString);

                    Date now = new Date();

                    //
                    // do we have at least an hour left on the token?
                    //
                    if (now.getTime() + 3600 < tokenExpirationDate.getTime()) {
                        accessToken = configurationManager.getAccessToken();
                        tokenExpiration = tokenExpirationDate.getTime();
                    } else {
                        getNewToken();
                    }
                } catch (ParseException pe) {
                    log.warn("can't parse token expiration - getting new token", pe);
                    getNewToken();
                }
            } else {
                getNewToken();
            }

            initialized.set(true);
        }
    }

    private void getNewToken() {
        ConfigurationManager configurationManager = new ConfigurationManager();


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

                    log.debug( "oauth token is " + oAuth2Token.toString() );

                    accessToken = oAuth2Token.getAccessToken();
                    tokenExpiration = new Date().getTime() + oAuth2Token.getExpiresIn();

                    Map<String, String> params = new Hashtable<>();
                    params.put("auth_token", accessToken);

                    GregorianCalendar currentTime = new GregorianCalendar();
                    currentTime.add(Calendar.SECOND, oAuth2Token.getExpiresIn() );
                    params.put("token_expiration", sdf.format(currentTime.getTime()) );
                    configurationManager.saveUpdatedParameters(params);
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
