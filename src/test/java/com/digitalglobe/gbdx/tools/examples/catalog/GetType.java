package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Type;


public class GetType {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        Type type = catalogManager.getType("DigitalGlobeProduct");

        System.out.println("type is " + type.toString());
    }
}