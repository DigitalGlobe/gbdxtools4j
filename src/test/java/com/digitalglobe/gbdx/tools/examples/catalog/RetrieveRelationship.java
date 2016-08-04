package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Relationship;


public class RetrieveRelationship {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        Relationship relationship = catalogManager.getRelationships("10100100057C1900");

        if( relationship != null )
            System.out.println( "got relationship of " + relationship.toString() );
        else
            System.out.println( "the given catalog id has no relationships");
    }
}