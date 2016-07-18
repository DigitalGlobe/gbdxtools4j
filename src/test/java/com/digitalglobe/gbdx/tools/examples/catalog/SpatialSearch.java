package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;


public class SpatialSearch {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();


        //
        // Spatial search
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withSearchAreaWkt( "POLYGON ((-122.41189956665039 37.59415685597818, -122.41189956665039 37.64460175855099, -122.34529495239259 37.64460175855099, -122.34529495239259 37.59415685597818, -122.41189956665039 37.59415685597818))")
                .withFilters(Collections.singletonList("offNadirAngle < 10"))
                .withTypes(Collections.singletonList("Acquisition"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }
    }
}
