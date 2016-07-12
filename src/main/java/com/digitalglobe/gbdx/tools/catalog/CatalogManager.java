package com.digitalglobe.gbdx.tools.catalog;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;
import com.digitalglobe.gbdx.tools.catalog.model.TraverseRequest;
import com.digitalglobe.gbdx.tools.communication.CommunicationBase;
import com.digitalglobe.gbdx.tools.config.ConfigurationManager;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    public SearchResponse search(SearchRequest searchRequest) throws IOException {

        Gson gson = new Gson();

        String searchRequestString = gson.toJson(searchRequest, SearchRequest.class);

        String searchRequestResultString = postData( baseUrl + "/search?includeRelationships=false", searchRequestString, true );

        if( StringUtils.trimToNull(searchRequestResultString) != null )
            return gson.fromJson(searchRequestResultString, SearchResponse.class);

        return null;
    }

    public Record getRecord( String catalogId ) throws IOException {
        String getUrl = baseUrl + "/record/" + catalogId + "?includeRelationships=false";

        String getRecordString = getData( getUrl, true );

        if(StringUtils.trimToNull(getRecordString) != null ) {
            Gson gson = new Gson();

            return gson.fromJson(getRecordString, Record.class);
        }

        return null;
    }

    public SearchResponse traverseFromRecord(TraverseRequest request) throws IOException{
        Gson gson = new Gson();

        String traverseRequestString = gson.toJson(request, TraverseRequest.class);

        String traverseResultString = postData( baseUrl + "/traverse", traverseRequestString, true );

        if(StringUtils.trimToNull(traverseResultString) != null )
            return gson.fromJson(traverseResultString, SearchResponse.class);

        return null;
    }

}