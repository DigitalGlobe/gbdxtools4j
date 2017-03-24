package com.digitalglobe.gbdx.tools.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the configuration of the gbdxtools4j library.
 */
public class ConfigurationManager {
    private static final Logger log = LoggerFactory.getLogger( ConfigurationManager.class );

    private static String authUrl = null;
    private static String userName = null;
    private static String password = null;
    private static String clientId = null;
    private static String clientSecret = null;
    private static String environment = null;
    private static String serviceBaseUrl = null;
    private static String accessToken = null;
    private static String tokenExpiration = null;

    private static final String DEFAULT_BASE_URL = "https://geobigdata.io";
    private static final String DEFAULT_AUTH_URL = DEFAULT_BASE_URL + "/auth/v1/oauth/token/";
    private static final String CONFIG_SECTION_NAME = "gbdx";
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static final Object lock = new Object();


    /**
     * Reads from the GBDX configuration file to get security related information.  The file
     * is in the users home directory named ".gbdx-config".  It is a very simple INI style
     * file that looks like:
     * <pre>
     *   [gbdx]
     *       client_id = &lt;client id from the GBDX web application&gt;
     *       client_secret = &lt;client secret from the GBDX web application&gt;
     *       user_name = &lt;your GBDX user name&gt;
     *       user_password = &lt;your GBDX password&gt;
     * </pre>
     *
     * <p>
     * All of these values come from the GBDX web application.
     * </p>
     *
     * <p>
     * Note that you can have a configuration section per environment if you'd like.  The
     * configuration name would be "gbdx-environment" where the environment part can be
     * set in the O/S environment or with a -D on the command line.
     * </p>
     *
     * <p>If the configuration file does not exist then the parameters can also come from
     * the operating system environment (i.e. <code>setenv</code>) and/or from Java system
     * parameters (i.e. -D parameters on the Java command line).  Even if the file exists
     * the parameters in it can be overridden on the command line or with environment variables.
     * The priority is system property, then environment variable and then the configuration file.
     * </p>
     */
    public ConfigurationManager() {
        synchronized (lock) {
            if( initialized.get() )
                return;

            File configFile = new File(System.getProperty("user.home") +
                    System.getProperty("file.separator") + ".gbdx-config");

            environment = getEnvOrSystemVar("ENVIRONMENT", environment);
            authUrl = DEFAULT_AUTH_URL;

            if (configFile.exists() && configFile.canRead()) {
                try (FileInputStream fis = new FileInputStream(configFile)) {
                    Ini ini = new Ini();
                    ini.load(fis);

                    String configSection = CONFIG_SECTION_NAME;
                    if (environment != null) {
                        configSection = CONFIG_SECTION_NAME + "-" + environment;
                    }

                    System.out.println("using configSection \"" + configSection + "\"");

                    Ini.Section section = ini.get(configSection);

                    if (section == null)
                        section = ini.get(CONFIG_SECTION_NAME);

                    if (section != null) {
                        authUrl = section.get("auth_url");
                        userName = section.get("user_name");
                        password = section.get("user_password");
                        clientId = section.get("client_id");
                        clientSecret = section.get("client_secret");
                        serviceBaseUrl = section.get("service_base_url");
                        tokenExpiration = section.get("token_expiration");
                        accessToken = section.get("access_token");
                    } else {
                        log.warn("No \"" + configSection + "\" section in the INI file \"" + configFile.getAbsolutePath() +
                                "\" - falling back to environment / system variables");
                    }
                } catch (IOException ioe) {
                    log.warn("can't open config file \"" + configFile.getAbsolutePath() +
                            "\" - falling back to environment / system variables", ioe);
                }
            }

            authUrl = getEnvOrSystemVar("GBDX_AUTH_URL", authUrl);
            userName = getEnvOrSystemVar("GBDX_USERNAME", userName);
            password = getEnvOrSystemVar("GBDX_PASSWORD", password);
            clientId = getEnvOrSystemVar("GBDX_CLIENT_ID", clientId);
            clientSecret = getEnvOrSystemVar("GBDX_CLIENT_SECRET", clientSecret);

            if (authUrl == null)
                throw new IllegalStateException("no authorization url configured");

            if (userName == null)
                throw new IllegalStateException("no user name configured");

            if (password == null)
                throw new IllegalStateException("no password configured");

            if (clientId == null)
                throw new IllegalStateException("no client id configured");

            if (clientSecret == null)
                throw new IllegalStateException("no client secret configured");

            initialized.set(true);
        }
    }

    /**
     * Get the URL used to call the authentication service.
     *
     * @return the url
     *
     */
    public String getAuthUrl() {
        return authUrl;
    }

    /**
     * Get the user name used for authentication.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Get the password used for authentication.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the client id used for authentication.
     *
     * @return the client id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Get the client secret used for authentication
     *
     * @return the client secret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Get the access token if it exists.
     *
     * @return the access token
     *
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Get the token expiration string if it exists.
     *
     * @return the token expiration string.
     *
     */
    public String getTokenExpiration() {
        return tokenExpiration;
    }


    /**
     * Gets the base part of the URL we'll use for communicating, taking account of the environment.
     *
     * @return the base url.
     */
    public String getBaseAPIUrl() {
        if( serviceBaseUrl != null)
            return serviceBaseUrl;

        if (environment != null)
            return "https://" + environment + ".geobigdata.io";

        return DEFAULT_BASE_URL;
    }

    /**
     * Gets a boolean to tell us if we're running in production or not.  This is determined
     * by seeing if the service_base_url is set to the same as the default.  If so, we're
     * in production.  If not, we're not in production.
     *
     * @return boolean that, if true, we're running in production
     */
    public boolean runningInProduction() {
        if( serviceBaseUrl != null && serviceBaseUrl.equals(DEFAULT_BASE_URL) )
            return true;

        return false;
    }

    /**
     * Convenience method to get a parameter from a system property, the O/S environment
     * or from the default value.  The order of precedence is:
     * <ol>
     *     <li>System property - i.e. a -D on the Java command line</li>
     *     <li>Environment variable</li>
     *     <li>The passed in default value</li>
     * </ol>
     *
     * @param variableName the name of the variable to get
     * @param defaultValue the default value if we can't find the system or environment version of the variable
     *
     * @return the value of the variable
     */
    private String getEnvOrSystemVar(String variableName, String defaultValue) {
        String value = System.getProperty(variableName);
        if (value == null) {
            value = System.getenv(variableName);
        }

        value = StringUtils.trimToNull(value);

        return (value == null ? defaultValue : value);
    }

    /**
     * Saves all parameters to the config file.  Any existing parameters are overwritten.
     *
     * @param values the new values to save.
     *
     */
    public void saveUpdatedParameters( Map<String, String> values ) {

        File configFile = new File(System.getProperty("user.home") +
                System.getProperty("file.separator") + ".gbdx-config");

        environment = getEnvOrSystemVar("ENVIRONMENT", environment);

        if (configFile.exists() && configFile.canRead()) {
            try {
                Ini ini = new Ini();
                ini.load(configFile);

                String configSection = CONFIG_SECTION_NAME;
                if( environment != null )
                    configSection = CONFIG_SECTION_NAME + "-" + environment;

                Ini.Section section = ini.get(configSection);

                if( section == null )
                    section = ini.get(CONFIG_SECTION_NAME);

                for( String nextKey: values.keySet() ) {
                    section.put(nextKey, values.get(nextKey));
                }

                ini.store(configFile);

            } catch (IOException ioe) {
                log.warn("can't open config file \"" + configFile.getAbsolutePath() + "\"", ioe);
            }
        }
    }
}
