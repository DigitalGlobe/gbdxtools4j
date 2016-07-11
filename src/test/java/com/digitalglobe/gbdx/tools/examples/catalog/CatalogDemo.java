package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;


public class CatalogDemo {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        System.out.println("heartbeat is " + catalogManager.isAlive());

    }
}
