package com.digitalglobe.gbdx.tools.catalogv2;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalogv2.model.SearchResponseV2;
import com.digitalglobe.gbdx.tools.communication.CommunicationBase;
import com.digitalglobe.gbdx.tools.config.ConfigurationManager;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;


/**
 * Manages the interface with the catalog v2 system.
 */
public class CatalogManagerV2 extends CommunicationBase {

    private static String baseUrl;


    public CatalogManagerV2() {
        ConfigurationManager configurationManager = new ConfigurationManager();

        baseUrl = configurationManager.getBaseAPIUrl() + "/catalog/v2";
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

    /**
     * Searches the catalog.
     *
     * @param searchRequest the parameters used to search the catalog
     *
     * @return a SearchResponse
     *
     * @throws IOException if there is an error communicating
     */
    public SearchResponseV2 search(SearchRequest searchRequest) throws IOException {

        Gson gson = new Gson();

        String searchRequestString = gson.toJson(searchRequest, SearchRequest.class);

        String searchRequestResultString = postData( baseUrl + "/search", searchRequestString, true );

        if( StringUtils.trimToNull(searchRequestResultString) != null )
            return gson.fromJson(searchRequestResultString, SearchResponseV2.class);

        return null;
    }

    /**
     * Gets a single record from the catalog.
     *
     * @param catalogId the catalogId to get
     *
     * @return the record if found, null if it isn't found
     *
     * @throws IOException if there is an error communicating
     */
    public Record getRecord(String catalogId ) throws IOException {
        String getUrl = baseUrl + "/record/" + catalogId + "?includeRelationships=false";

        String getRecordString = getData( getUrl, true );

        if(StringUtils.trimToNull(getRecordString) != null ) {
            Gson gson = new Gson();

            return gson.fromJson(getRecordString, Record.class);
        }

        return null;
    }
}