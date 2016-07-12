package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Arrays;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;
import com.digitalglobe.gbdx.tools.catalog.model.TraverseRequest;


public class CatalogDemo {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        System.out.println("heartbeat is " + catalogManager.isAlive());


        //
        // simple search
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withSearchAreaWkt( "POLYGON ((-122.41189956665039 37.59415685597818, -122.41189956665039 37.64460175855099, -122.34529495239259 37.64460175855099, -122.34529495239259 37.59415685597818, -122.41189956665039 37.59415685597818))")
                .withStartDate("2004-01-01T00:00:00.000Z")
                .withEndDate("2015-04-30T23:59:59.999Z")
                .withFilters(Arrays.asList("(sensorPlatformName = 'WORLDVIEW01' OR sensorPlatformName ='QUICKBIRD02')",
                        "cloudCover < 10",
                        "offNadirAngle < 10"))
                 .withTypes(Arrays.asList("Acquisition"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }


        //
        // Spatial search
        //
        searchRequest = new SearchRequest();

        searchRequest.withSearchAreaWkt( "POLYGON ((-122.41189956665039 37.59415685597818, -122.41189956665039 37.64460175855099, -122.34529495239259 37.64460175855099, -122.34529495239259 37.59415685597818, -122.41189956665039 37.59415685597818))")
                .withFilters(Arrays.asList("offNadirAngle < 10"))
                .withTypes(Arrays.asList("Acquisition"));

        response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }

        //
        // date range search
        //
   /*     searchRequest = new SearchRequest();

        searchRequest.withStartDate("2015-04-30T00:00:00.000Z")
                .withEndDate("2015-04-30T23:59:59.999Z")
                .withFilters(Arrays.asList("cloudCover < 10"))
                .withTypes(Arrays.asList("Acquisition"));

        response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        } */

        //
        // Search for Vendor by Name
        //
        searchRequest = new SearchRequest();

        searchRequest.withFilters(Arrays.asList("name like 'D%'"))
                .withTypes(Arrays.asList("Vendor"));

        response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }

        Record record = catalogManager.getRecord("10100100057C1900");
        if( record != null ) {
            System.out.println( "got record of " + record.toString() );
        }
        else
            System.out.println("no records found");


        TraverseRequest traverseRequest = new TraverseRequest();
        traverseRequest.withRootRecordId("10100100057C1900")
                        .withMaxdepth(2)
                        .withDirection("both")
                        .withLabels(Arrays.asList("_imageFiles"));

        SearchResponse searchResponse = catalogManager.traverseFromRecord(traverseRequest);

        if( searchResponse != null ) {
            System.out.println( "got searchResponse of " + searchResponse.toString() );
        }
        else
            System.out.println("no searchResponse found");
    }
}
