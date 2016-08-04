package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.UUID;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;


public class CreateRecord {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        Record record = new Record().withIdentifier(UUID.randomUUID().toString())
                                    .withType("DigitalGlobeAcquisition")
                                    .withProperty( "vendorName", "DigitalGlobe")
                                    .withProperty("acquisitionDate", "Fri Feb 06 15:03:50 MST 2015");

        Record returnedRecord = catalogManager.createRecord(record);

        System.out.println( "got record of " + returnedRecord.toString() );
    }
}
