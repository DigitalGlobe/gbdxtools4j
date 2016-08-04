package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;
import java.util.List;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Type;


public class ListTypes {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        List<Type> types = catalogManager.listAllTypes();

        for( Type nextType: types) {
            System.out.println("next type is " + nextType.toString());
        }
    }
}
