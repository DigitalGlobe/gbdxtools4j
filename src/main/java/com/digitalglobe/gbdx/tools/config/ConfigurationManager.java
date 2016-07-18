package com.digitalglobe.gbdx.tools.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the configuration of the gbdxtools4j library.
 */
public class ConfigurationManager {
    private static final Logger log = LoggerFactory.getLogger( ConfigurationManager.class );

    private String authUrl = null;
    private String userName = null;
    private String password = null;
    private String clientId = null;
    private String clientSecret = null;
    private String environment = null;

    private static final String DEFAULT_BASE_URL = "https://geobigdata.io";
    private static final String DEFAULT_AUTH_URL = DEFAULT_BASE_URL + "/auth/v1/oauth/token/";
    private static final String CONFIG_SECTION_NAME = "gbdx";


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
        File configFile = new File(System.getProperty("user.home") +
                System.getProperty("file.separator") + ".gbdx-config");

        environment = getEnvOrSystemVar("ENVIRONMENT", environment);

        if (configFile.exists() && configFile.canRead()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                Ini ini = new Ini();
                ini.load(fis);

                String configSection;
                if( environment != null )
                    configSection = CONFIG_SECTION_NAME + environment;
                else
                    configSection = CONFIG_SECTION_NAME;

                Ini.Section section = ini.get(configSection);

                if( section == null )
                    section = ini.get(CONFIG_SECTION_NAME);

                if (section != null) {
                    authUrl = section.get("auth_url");
                    userName = section.get("user_name");
                    password = section.get("user_password");
                    clientId = section.get("client_id");
                    clientSecret = section.get("client_secret");
                }
                else {
                    log.warn("No \"gbdx\" section in the INI file \"" + configFile.getAbsolutePath() +
                            "\" - falling back to environment / system variables");
                }
            } catch (IOException ioe) {
                log.warn("can't open config file \"" + configFile.getAbsolutePath() +
                        "\" - falling back to environment / system variables", ioe);
            }
        }

        authUrl = getEnvOrSystemVar("GBDX_AUTH_URL", DEFAULT_AUTH_URL);
        userName = getEnvOrSystemVar("GBDX_USERNAME", userName);
        password = getEnvOrSystemVar("GBDX_PASSWORD", password);
        clientId = getEnvOrSystemVar("GBDX_CLIENT_ID", clientId);
        clientSecret = getEnvOrSystemVar("GBDX_CLIENT_SECRET", clientSecret);

        if( authUrl == null )
            throw new IllegalStateException("no authorization url configured");

        if( userName == null )
            throw new IllegalStateException("no user name configured");

        if( password == null )
            throw new IllegalStateException("no password configured");

        if( clientId == null )
            throw new IllegalStateException("no client id configured");

        if( clientSecret == null )
            throw new IllegalStateException("no client secret configured");
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
     * Gets the base part of the URL we'll use for communicating, taking account of the environment.
     *
     * @return the base url.
     */
    public String getBaseAPIUrl() {
        if (environment != null)
            return "https://" + environment + ".geobigdata.io";

        return DEFAULT_BASE_URL;
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
}
