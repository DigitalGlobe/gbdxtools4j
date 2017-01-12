package com.digitalglobe.gbdx.tools.examples.catalogv2;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.catalogv2.CatalogManagerV2;


public class CatalogHeartbeatV2 {
    public static void main(String[] argv) throws IOException {
        CatalogManagerV2 catalogManager = new CatalogManagerV2();

        if( catalogManager.isAlive() )
            System.out.println("catalog system is up and running");
        else
            System.out.println("unable to get a heartbeat from the catalog system");
    }
}
