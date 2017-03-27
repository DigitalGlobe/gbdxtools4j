package com.digitalglobe.gbdx.tools.examples.idaho;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.idaho.IdahoManager;


public class IdahoHeartbeat {
    public static void main(String[] argv) throws IOException {
        IdahoManager idahoManager = new IdahoManager();

        if( idahoManager.isAlive() )
            System.out.println("idaho system is up and running");
        else
            System.out.println("unable to get a heartbeat from the idaho system");
    }
}
