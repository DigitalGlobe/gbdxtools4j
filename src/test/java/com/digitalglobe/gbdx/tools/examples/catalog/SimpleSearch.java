package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;


public class SimpleSearch {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

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
                 .withTypes(Collections.singletonList("Acquisition"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }

    }
}
