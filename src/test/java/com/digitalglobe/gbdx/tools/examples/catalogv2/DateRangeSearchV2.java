package com.digitalglobe.gbdx.tools.examples.catalogv2;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalogv2.CatalogManagerV2;
import com.digitalglobe.gbdx.tools.catalogv2.model.RecordV2;
import com.digitalglobe.gbdx.tools.catalogv2.model.SearchResponseV2;


public class DateRangeSearchV2 {
    public static void main(String[] argv) throws IOException {
        CatalogManagerV2 catalogManager = new CatalogManagerV2();

        //
        // date range search
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withStartDate("2015-04-30T00:00:00.000Z")
                .withEndDate("2015-04-30T23:59:59.999Z")
                .withLimit(5)
                .withFilters(Collections.singletonList("cloudCover < 10"))
                .withTypes(Collections.singletonList("Acquisition"));

        SearchResponseV2 response = catalogManager.search(searchRequest);

        System.out.println( "got a total of " + response.getStats().getRecordsReturned() + " records returned" );
        for( RecordV2 nextRecord: response.getResults() ) {
            System.out.println( "got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"" );
        }
    }
}
