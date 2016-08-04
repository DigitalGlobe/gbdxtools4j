package com.digitalglobe.gbdx.tools.examples.catalog;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalog.CatalogManager;
import com.digitalglobe.gbdx.tools.catalog.model.Property;
import com.digitalglobe.gbdx.tools.catalog.model.Type;


public class CreateType {
    public static void main(String[] argv) throws IOException {
        CatalogManager catalogManager = new CatalogManager();

        Type type = new Type()
                            .withDescription("Sample Type for Demo")
                            .withIdentifier("SampleType")
                            .withSuperClass("GBDDomainObject")
                            .withProperty(new Property("aProperty", "Integer", "daProperty"));

        Type returnedType = catalogManager.createType(type);

        System.out.println( "type is " + returnedType.toString() );


    }
}
