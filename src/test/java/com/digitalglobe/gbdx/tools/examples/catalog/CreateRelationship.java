package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.UUID;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Record;
import com.digitalglobe.gbdx.tools.catalog.model.Relationship;


public class CreateRelationship {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        Record record = new Record().withIdentifier(UUID.randomUUID().toString())
                .withType("GBDDomainObject")
                .withProperty( "int_prop", "DigitalGlobe")
                .withProperty("acquisitionDate", "Fri Feb 06 15:03:50 MST 2015");

        Record returnedRecord = catalogManager.createRecord(record);

        Relationship relationship = new Relationship().withFromObjectId("");

        if( relationship != null )
            System.out.println( "got relationship of " + relationship.toString() );
        else
            System.out.println( "the given catalog id has no relationships");
    }
}