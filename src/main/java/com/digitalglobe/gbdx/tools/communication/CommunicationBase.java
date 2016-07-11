package com.digitalglobe.gbdx.tools.communication;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;

import com.digitalglobe.gbdx.tools.auth.GBDXAuthManager;
import com.digitalglobe.gbdx.tools.config.ConfigurationManager;
import com.google.gson.Gson;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Common place for some useful communication config.
 */
public class CommunicationBase {

    private GBDXAuthManager gbdxAuthManager;
    private ConfigurationManager configurationManager;

    /**
     * Initializes the communication base.
     */
    protected CommunicationBase() {
        gbdxAuthManager = new GBDXAuthManager();
        configurationManager = new ConfigurationManager();
    }

    /**
     * Gets data from the given url.
     *
     * @param url the url to send the jsonString to
     * @param requiresAuth indicates if we need to send the auth header for this call
     *
     * @return the body returned by the HTTP post.
     *
     * @throws IOException if there was an error (a non-200) return value from the HTTP post
     *
     */
    protected String getData(String url, boolean requiresAuth) throws IOException {

        try (CloseableHttpClient closeableHttpClient = getHttpClient()) {
            HttpGet httpGet = new HttpGet(url);

            if (requiresAuth)
                httpGet.addHeader("Authorization", "Bearer " + gbdxAuthManager.getAccessToken());

            try (CloseableHttpResponse response = closeableHttpClient.execute(httpGet)) {

                int httpResponseCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (httpResponseCode != 200) {

                    if( httpResponseCode == 401 ) {
                        Gson gson = new Gson();
                        ErrorMessage errorMessage = gson.fromJson( responseBody, ErrorMessage.class);
                        throw new SecurityException("Your credentials are not able to access this resource.  Reason: "
                                                     + errorMessage.getMessage() );
                    }

                    throw new IOException("HTTP GET got non-200 response of " + httpResponseCode + " with body of \"" + responseBody + "\"");
                }

                return responseBody;
            }
        }
    }

    /**
     * Post data to the given url.
     *
     * @param url the url to send the jsonString to
     * @param jsonString the data to send
     * @param requiresAuth indicates if we need to send the auth header for this call
     *
     * @return the body returned by the HTTP post.
     *
     * @throws IOException if there was an error (a non-200) return value from the HTTP post
     *
     */
    protected String postData(String url, String jsonString, boolean requiresAuth) throws IOException {
        try (CloseableHttpClient closeableHttpClient = getHttpClient()) {
            HttpPost httpPost = new HttpPost(url);

            if (requiresAuth)
                httpPost.addHeader("Authorization", "Bearer " + gbdxAuthManager.getAccessToken());

            StringEntity stringEntity = new StringEntity(jsonString);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);

            try (CloseableHttpResponse response = closeableHttpClient.execute(httpPost)) {

                int httpResponseCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (httpResponseCode != 200) {
                    throw new IOException("HTTP POST got non-200 response of " +
                                          httpResponseCode + " with body of \"" + responseBody + "\"");
                }

                return responseBody;
            }
        }
    }

    /**
     * Delete from a url.
     *
     * @param url the url to send the jsonString to
     * @param requiresAuth indicates if we need to send the auth header for this call
     *
     * @return the body returned by the HTTP post.
     *
     * @throws IOException if there was an error (a non-200) return value from the HTTP post
     *
     */
    protected String delete(String url, boolean requiresAuth) throws IOException {
        try (CloseableHttpClient closeableHttpClient = getHttpClient()) {
            HttpDelete httpDelete = new HttpDelete(url);

            if (requiresAuth)
                httpDelete.addHeader("Authorization", "Bearer " + gbdxAuthManager.getAccessToken());

            try (CloseableHttpResponse response = closeableHttpClient.execute(httpDelete)) {

                int httpResponseCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (httpResponseCode != 200) {
                    throw new IOException("HTTP DELETE got non-200 response of " +
                                          httpResponseCode + " with body of \"" + responseBody + "\"");
                }

                return responseBody;
            }
        }
    }

    /**
     * Gets a ClosableHttpClient.  If the environment variable for the environment
     * (huh?) is set then we assume that we should skip SSL validation.  The terminology
     * is a bit over loaded here.  For development purposes, a GBDX user may have a
     * private "environment" - a full GBDX suite that is private to that user.  This
     * is called an environment and it is named.  This method sees if the environment
     * is set and, if so, skips SSL validation as the development environments all use
     * self-signed certificates.
     *
     * @return the ClosableHttpClient
     * @throws InternalError if we are unable to setup the SSL "don't validate" HttpClient
     *
     */
    private CloseableHttpClient getHttpClient() {
        if (configurationManager.getEnvironment() != null) {
            try {
                SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
                sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
                HostnameVerifier hostnameVerifierAllowAll = (hostname, session) -> true;
                SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(),
                        hostnameVerifierAllowAll);
                return HttpClients.custom()
                        .setSSLSocketFactory(sslSocketFactory)
                        .build();
            } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
                throw new InternalError( "Can't setup fake SSL verifier", e);
            }
        } else {
            return HttpClientBuilder.create().build();
        }
    }

}
