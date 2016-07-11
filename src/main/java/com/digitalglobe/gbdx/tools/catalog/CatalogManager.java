package com.digitalglobe.gbdx.tools.catalog;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digitalglobe.gbdx.tools.communication.CommunicationBase;
import com.digitalglobe.gbdx.tools.config.ConfigurationManager;


/**
 * Manages the interface with the workflow system.
 */
public class CatalogManager extends CommunicationBase {
    private static final Logger log = LoggerFactory.getLogger(CatalogManager.class);

    private static String baseUrl = "https://geobigdata.io/catalog/v1";

    public CatalogManager() {
        ConfigurationManager configurationManager = new ConfigurationManager();

        String env = configurationManager.getEnvironment();

        if (env != null) {
            baseUrl = "https://" + env + ".geobigdata.io/catalog/v1";
        }
    }

    /**
     * Calls /catalog/v1/heartbeat to check if the catalog system is alive
     *
     * @return true if we get an "ok", false otherwise.
     *
     * @throws IOException if there is an error communicating
     *
     */
    public boolean isAlive() throws IOException {
        String okString = getData(baseUrl + "/heartbeat", false);

        if (okString != null) {
            if ((okString.trim().toLowerCase().contains("ok")))
                return true;
        }

        return false;
    }
}