package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.SearchRequest;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;


public class VendorSearch {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        //
        // Search for Vendor by Name
        //
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.withFilters(Collections.singletonList("name like 'D%'"))
                .withTypes(Collections.singletonList("Vendor"));

        SearchResponse response = catalogManager.search(searchRequest);

        System.out.println("got a total of " + response.getStats().getRecordsReturned() + " records returned");
        for (Record nextRecord : response.getResults()) {
            System.out.println("got record id of \"" + nextRecord.getIdentifier() + "\" of type \"" + nextRecord.getType() + "\"");
        }
    }
}
