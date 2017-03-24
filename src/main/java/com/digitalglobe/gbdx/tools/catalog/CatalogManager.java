package com.digitalglobe.gbdx.tools.catalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.Relationship;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;
import com.digitalglobe.gbdx.tools.catalog.model.TraverseRequest;
import com.digitalglobe.gbdx.tools.catalog.model.Type;
import com.digitalglobe.gbdx.tools.communication.CommunicationBase;
import com.digitalglobe.gbdx.tools.communication.ErrorMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;


/**
 * Manages the interface with the catalog system.
 */
public class CatalogManager extends CommunicationBase {

    private static String baseUrl;


    public CatalogManager() {
        baseUrl = configurationManager.getBaseAPIUrl() + "/catalog/v1";
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
    public SearchResponse search(SearchRequest searchRequest) throws IOException {

        Gson gson = new Gson();

        String searchRequestString = gson.toJson(searchRequest, SearchRequest.class);

        String searchRequestResultString = postData( baseUrl + "/search?includeRelationships=false", searchRequestString, true );

        if( StringUtils.trimToNull(searchRequestResultString) != null )
            return gson.fromJson(searchRequestResultString, SearchResponse.class);

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
    public Record getRecord( String catalogId ) throws IOException {
        String getUrl = baseUrl + "/record/" + catalogId + "?includeRelationships=false";

        String getRecordString = getData( getUrl, true );

        if(StringUtils.trimToNull(getRecordString) != null ) {
            Gson gson = new Gson();

            return gson.fromJson(getRecordString, Record.class);
        }

        return null;
    }

    /**
     * Traverse from a record.
     *
     * @param traverseRequest the request to do the traverse
     *
     * @return a SearchResponse related to the traversal.
     *
     * @throws IOException if there is an error communicating
     */
    public SearchResponse traverseFromRecord(TraverseRequest traverseRequest) throws IOException{
        Gson gson = new Gson();

        String traverseRequestString = gson.toJson(traverseRequest, TraverseRequest.class);

        String traverseResultString = postData( baseUrl + "/traverse", traverseRequestString, true );

        if(StringUtils.trimToNull(traverseResultString) != null )
            return gson.fromJson(traverseResultString, SearchResponse.class);

        return null;
    }

    /**
     * Creates a type in the GBDX catalog.
     *
     * @param type the type to create
     *
     * @return the created type.  It will be the same as the passed in type other than the owner will be the
     *         GBDX user of the caller.
     *
     * @throws IOException if there is an error communicating
     */
    public Type createType( Type type ) throws IOException {
        Gson gson = new Gson();

        String createTypeString = postData( baseUrl + "/type", gson.toJson(type, Type.class), true );

        return getType(type.getIdentifier());
    }

    /**
     * Remove a type in the GBDX catalog.
     *
     * @param type the type to remove
     *
     * @return true if the type was removed, false otherwise
     *
     * @throws IOException if there is an error communicating
     */
    public boolean removeType( Type type ) throws IOException {
        String deleteUrl = baseUrl + "/type/" + type.getIdentifier();

        ErrorMessage errorMessage = delete(deleteUrl, true);

        if( errorMessage != null ) {
            if( (errorMessage.getStatus() != null) && (errorMessage.getStatus().contains("404")))
                return false;
        }

        return true;
    }

    /**
     * List all catalog types
     *
     * @return a List of Types
     *
     * @throws IOException if there is an error communicating
     */
    public List<Type> listAllTypes() throws IOException {
        String getTypesString = getData( baseUrl + "/type", true );

        java.lang.reflect.Type listType = new TypeToken<ArrayList<Type>>(){}.getType();

        return new Gson().fromJson(getTypesString, listType);
    }

    /**
     * Get a single catalog type
     *
     * @return the Type
     *
     * @throws IOException if there is an error communicating
     */
    public Type getType(String identifier) throws IOException {
        String getTypeString = getData( baseUrl + "/type/" + identifier, true );

        return new Gson().fromJson(getTypeString, Type.class);
    }

    /**
     * Creates a record in the GBDX catalog.
     *
     * @param record the record to create
     *
     * @return the created record.  It will be the same as the passed in record other than the owner will be the
     *         GBDX user of the caller.
     *
     * @throws IOException if there is an error communicating
     */
    public Record createRecord( Record record ) throws IOException {
        Gson gson = new Gson();

        String createRecordString = postData( baseUrl + "/record", gson.toJson(record, Record.class), true );

        return gson.fromJson(createRecordString, Record.class);
    }

    /**
     * Updates a record in the GBDX catalog.
     *
     * @param record the record to update.
     *
     * @return the updated record
     *
     * @throws IOException if there is an error communicating
     */

    public Record updateRecord( Record record ) throws IOException {
        Gson gson = new Gson();

        String updateRecordString = putData( baseUrl + "/record", gson.toJson(record, Record.class), true );

        return gson.fromJson(updateRecordString, Record.class);
    }

    /**
     * Removes a record from the GBDX catalog.
     *
     * @param record the record to remove.
     *
     * @return true if the record was removed, false if not
     *
     * @throws IOException if there is an error communicating
     */

    public boolean removeRecord( Record record ) throws IOException {
        return removeRecord( record.getIdentifier() );
    }

    /**
     * Removes a record from the GBDX catalog.
     *
     * @param identifier the identifier of the record to remove.
     *
     * @return true if the record was removed, false if not
     *
     * @throws IOException if there is an error communicating
     */

    public boolean removeRecord( String identifier ) throws IOException {
        String deleteUrl = baseUrl + "/record/" + identifier + "?includeRelationships=false";

        ErrorMessage errorMessage = delete(deleteUrl, true);

        if( errorMessage != null ) {
            if( (errorMessage.getStatus() != null) && (errorMessage.getStatus().contains("404")))
                return false;
        }

        return true;
    }

    public void createRelationship( Relationship relationship ) throws IOException {
        Gson gson = new Gson();

        try {
            String createRelationshipString = postData(baseUrl + "/relationship", gson.toJson(relationship, Relationship.class), true);
        }
        catch (IOException ioe) {
            System.out.println( "exception is " + ioe.getMessage());
        }

    }

    /**
     * Get a Relationship from a catalogs identifier.
     *
     * @param identifier the identifier to get the relationship for.
     *
     * @return a Relationship class if a relationship can be found or null if not.
     *
     * @throws IOException if there is an error communicating
     */
    public Relationship getRelationships(String identifier) throws IOException {
        String getRelationshipString;

        try {
            getRelationshipString = getData( baseUrl + "/relationship/" + identifier, true );
            return new Gson().fromJson(getRelationshipString, Relationship.class);
        }
        catch( IOException ioe ) {
            if( ioe.getMessage().contains("404"))
                return null;

            throw ioe;
        }
    }

    public boolean removeRelationship(String identifier) throws IOException {
        String deleteUrl = baseUrl + "/relationship/" + identifier;

        ErrorMessage errorMessage = delete(deleteUrl, true);

        if( errorMessage != null ) {
            if( (errorMessage.getStatus() != null) && (errorMessage.getStatus().contains("404")))
                return false;
        }

        return true;
    }
}