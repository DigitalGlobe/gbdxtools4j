package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;


public class DateRangeSearch {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        //
        // date range search
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withStartDate("2015-04-30T00:00:00.000Z")
                .withEndDate("2015-04-30T23:59:59.999Z")
                .withFilters(Collections.singletonList("cloudCover < 10"))
                .withTypes(Collections.singletonList("Acquisition"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( Record nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }
    }
}
