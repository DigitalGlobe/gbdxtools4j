package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;


public class CatalogHeartbeat {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        if( catalogManager.isAlive() )
            System.out.println("catalog system is up and running");
        else
            System.out.println("unable to get a heartbeat from the catalog system");
    }
}
