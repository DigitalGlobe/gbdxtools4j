package com.digitalglobe.gbdx.tools.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    private static String authUrl;
    private static String userName;
    private static String password;
    private static String clientId;
    private static String clientSecret;
    private static String environment;
    private static String serviceBaseUrl;
    private static String accessToken;
    private static ZonedDateTime accessTokenExpiration;

    private static final String DEFAULT_BASE_URL = "https://geobigdata.io";
    private static final String DEFAULT_AUTH_URL = DEFAULT_BASE_URL + "/auth/v1/oauth/token/";

    //
    // ini definitions
    //
    private static final String CONFIG_SECTION_NAME = "gbdx";
    private static final String AUTH_URL_INI_NAME = "auth_url";
    private static final String USER_NAME_INI_NAME = "user_name";
    private static final String PASSWORD_INI_NAME = "user_password";
    private static final String CLIENT_ID_INI_NAME = "client_id";
    private static final String CLIENT_SECRET_INI_NAME = "client_secret";
    private static final String SERVICE_BASE_URL_INI_NAME = "service_base_url";
    private static final String ACCESS_TOKEN_EXPIRATION_INI_NAME = "access_token_expiration";
    private static final String ACCESS_TOKEN_INI_NAME = "gbdx_token";

    //
    // env/system definitions
    //
    private static final String AUTH_URL_ENV_NAME = "GBDX_AUTH_URL";
    private static final String USER_NAME_ENV_NAME = "GBDX_USERNAME";
    private static final String PASSWORD_ENV_NAME = "GBDX_PASSWORD";
    private static final String CLIENT_ID_ENV_NAME = "GBDX_CLIENT_ID";
    private static final String CLIENT_SECRET_ENV_NAME = "GBDX_CLIENT_SECRET";
    private static final String ACCESS_TOKEN_ENV_NAME = "GBDX_ACCESS_TOKEN";

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
     * <p>
     * The parameters can instead or in addition come from the operating system environment
     * (i.e. <code>setenv</code>) and/or from Java system parameters (i.e. -D parameters on the
     * Java command line).  Even if the file exists the parameters in it can be overridden on
     * the command line or with environment variables. The priority is system property, then
     * environment variable and then the configuration file.
     * </p>
     *
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
                        authUrl = section.get(AUTH_URL_INI_NAME);
                        userName = section.get(USER_NAME_INI_NAME);
                        password = section.get(PASSWORD_INI_NAME);
                        clientId = section.get(CLIENT_ID_INI_NAME);
                        clientSecret = section.get(CLIENT_SECRET_INI_NAME);
                        serviceBaseUrl = section.get(SERVICE_BASE_URL_INI_NAME);
                        String tokenExpirationString = section.get(ACCESS_TOKEN_EXPIRATION_INI_NAME);
                        if(tokenExpirationString != null)
                            accessTokenExpiration =  ZonedDateTime.parse(tokenExpirationString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                        accessToken = section.get(ACCESS_TOKEN_INI_NAME);
                    } else {
                        log.warn("No \"" + configSection + "\" section in the INI file \"" + configFile.getAbsolutePath() +
                                "\" - falling back to environment / system variables");
                    }
                } catch (IOException ioe) {
                    log.warn("can't open config file \"" + configFile.getAbsolutePath() +
                            "\" - falling back to environment / system variables", ioe);
                }
            }

            authUrl = getEnvOrSystemVar(AUTH_URL_ENV_NAME, authUrl);
            userName = getEnvOrSystemVar(USER_NAME_ENV_NAME, userName);
            password = getEnvOrSystemVar(PASSWORD_ENV_NAME, password);
            clientId = getEnvOrSystemVar(CLIENT_ID_ENV_NAME, clientId);
            clientSecret = getEnvOrSystemVar(CLIENT_SECRET_ENV_NAME, clientSecret);
            accessToken = getEnvOrSystemVar(ACCESS_TOKEN_ENV_NAME, accessToken);

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
     * Set the access token.
     *
     * @param token the new access token
     *
     */
    public void setAccessToken(String token) {
        accessToken = token;
    }

    /**
     * Get the access token expiration if it exists.
     *
     * @return the token expiration.
     *
     */
    public ZonedDateTime getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    /**
     * Set the access token expiration.
     *
     * @param expiration the expiration of the token
     *
     */
    public void setAccessTokenExpiration(ZonedDateTime expiration) {
        accessTokenExpiration = expiration;
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
        return (serviceBaseUrl != null) && serviceBaseUrl.equals(DEFAULT_BASE_URL);
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
     */
    public void saveUpdatedParameters() {

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

                //
                // only token and token expiration can be set here - only worry about those two
                //
                if( getAccessToken() != null ) {
                    section.put(ACCESS_TOKEN_INI_NAME, getAccessToken());

                    if (getAccessTokenExpiration() != null)
                        section.put(ACCESS_TOKEN_EXPIRATION_INI_NAME,
                                    DateTimeFormatter.ISO_ZONED_DATE_TIME.format(getAccessTokenExpiration()));
                }

                ini.store(configFile);

            } catch (IOException ioe) {
                log.warn("can't open config file \"" + configFile.getAbsolutePath() + "\"", ioe);
            }
        }
    }
}
