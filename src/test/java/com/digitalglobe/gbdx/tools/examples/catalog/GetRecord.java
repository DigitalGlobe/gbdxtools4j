package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;


public class GetRecord {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        Record record = catalogManager.getRecord("10100100057C1900");
        if( record != null ) {
            System.out.println( "got record of " + record.toString() );
        }
        else
            System.out.println("no records found");
    }
}
