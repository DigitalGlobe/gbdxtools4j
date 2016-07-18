package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.Collections;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.SearchResponse;
import com.digitalglobe.gbdx.tools.catalog.model.TraverseRequest;


public class TraverseRecord {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        TraverseRequest traverseRequest = new TraverseRequest();
        traverseRequest.withRootRecordId("10100100057C1900")
                        .withMaxdepth(2)
                        .withDirection("both")
                        .withLabels(Collections.singletonList("_imageFiles"));

        SearchResponse searchResponse = catalogManager.traverseFromRecord(traverseRequest);

        if( searchResponse != null ) {
            System.out.println( "got searchResponse of " + searchResponse.toString() );
        }
        else
            System.out.println("no searchResponse found");
    }
}