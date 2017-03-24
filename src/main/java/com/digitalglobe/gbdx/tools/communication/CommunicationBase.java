package com.digitalglobe.gbdx.tools.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;

import com.digitalglobe.gbdx.tools.auth.GBDXAuthManager;
import com.digitalglobe.gbdx.tools.config.ConfigurationManager;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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

    protected ConfigurationManager configurationManager;

    /**
     * Initializes the communication base.
     */
    @SuppressWarnings("unused")
    protected CommunicationBase() {
        GBDXAuthManager gbdxAuthManager = new GBDXAuthManager();
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

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        getData(url, byteArrayOutputStream, requiresAuth);

        return byteArrayOutputStream.toString();
    }

    /**
     * Gets data from the given url.
     *
     * @param url the url to send the jsonString to
     * @param outputStream an OutputStream to copy the data from the call to
     * @param requiresAuth indicates if we need to send the auth header for this call
     *
     * @throws IOException if there was an error (a non-200) return value from the HTTP post
     *
     */
    protected void getData(String url, OutputStream outputStream, boolean requiresAuth) throws IOException {

        try (CloseableHttpClient closeableHttpClient = getHttpClient()) {
            HttpGet httpGet = new HttpGet(url);

            if (requiresAuth)
                httpGet.addHeader("Authorization", "Bearer " + configurationManager.getAccessToken());

            try (CloseableHttpResponse getResponse = closeableHttpClient.execute(httpGet)) {

                int httpResponseCode = getResponse.getStatusLine().getStatusCode();

                if (httpResponseCode != 200) {
                    if( httpResponseCode == 401 ) {
                        throw new SecurityException("Your credentials are not able to access this resource" );
                    }

                    throw new IOException("HTTP GET got non-200 response of " + httpResponseCode );
                }

                IOUtils.copy(getResponse.getEntity().getContent(), outputStream);
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

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        postData(url, jsonString, byteArrayOutputStream, requiresAuth);

        return byteArrayOutputStream.toString();
    }

    /**
     * Post data to the given url.
     *
     * @param url the url to send the jsonString to
     * @param jsonString the data to send
     * @param outputStream an OutputStream to copy the data from the call to
     * @param requiresAuth indicates if we need to send the auth header for this call
     *
     * @throws IOException if there was an error (a non-200) return value from the HTTP post
     *
     */
    protected void postData(String url, String jsonString, OutputStream outputStream, boolean requiresAuth) throws IOException {

        try (CloseableHttpClient closeableHttpClient = getHttpClient()) {
            HttpPost httpPost = new HttpPost(url);

            if (requiresAuth)
                httpPost.addHeader("Authorization", "Bearer " + configurationManager.getAccessToken());

            StringEntity stringEntity = new StringEntity(jsonString);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);

            try (CloseableHttpResponse postResponse = closeableHttpClient.execute(httpPost)) {

                int httpResponseCode = postResponse.getStatusLine().getStatusCode();

                if (httpResponseCode != 200) {
                    if( httpResponseCode == 401 ) {
                        throw new SecurityException("Your credentials are not able to access this resource" );
                    }

                    throw new IOException("HTTP GET got non-200 response of " + httpResponseCode );
                }

                IOUtils.copy(postResponse.getEntity().getContent(), outputStream);
            }
        }
    }

    /**
     * Put data to the given url.
     *
     * @param url the url to send the jsonString to
     * @param jsonString the data to send
     * @param requiresAuth indicates if we need to send the auth header for this call
     *
     * @return the body returned by the HTTP put.
     *
     * @throws IOException if there was an error (a non-200) return value from the HTTP post
     *
     */
    protected String putData(String url, String jsonString, boolean requiresAuth) throws IOException {
        try (CloseableHttpClient closeableHttpClient = getHttpClient()) {
            HttpPut httpPut = new HttpPut(url);

            if (requiresAuth)
                httpPut.addHeader("Authorization", "Bearer " + configurationManager.getAccessToken());

            StringEntity stringEntity = new StringEntity(jsonString);
            stringEntity.setContentType("application/json");
            httpPut.setEntity(stringEntity);

            try (CloseableHttpResponse response = closeableHttpClient.execute(httpPut)) {

                int httpResponseCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                if (httpResponseCode != 200) {
                    throw new IOException("HTTP PUT got non-200 response of " +
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
    protected ErrorMessage delete(String url, boolean requiresAuth) throws IOException {
        try (CloseableHttpClient closeableHttpClient = getHttpClient()) {
            HttpDelete httpDelete = new HttpDelete(url);

            if (requiresAuth)
                httpDelete.addHeader("Authorization", "Bearer " + configurationManager.getAccessToken());

            try (CloseableHttpResponse response = closeableHttpClient.execute(httpDelete)) {

                int httpResponseCode = response.getStatusLine().getStatusCode();

                String responseBody = null;
                if( response.getEntity() != null )
                    responseBody = EntityUtils.toString(response.getEntity());

                if (httpResponseCode / 100 != 2) {
                    return new Gson().fromJson(responseBody, ErrorMessage.class);
                }

                return null;
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
        if (!configurationManager.runningInProduction()) {
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
                throw new RuntimeException( "Can't setup fake SSL verifier", e);
            }
        } else {
            return HttpClientBuilder.create().build();
        }
    }

}
