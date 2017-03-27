package com.digitalglobe.gbdx.tools.examples.idaho;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.digitalglobe.gbdx.tools.idaho.IdahoManager;

public class GetImage {
    public static void main(String[] argv) {
        IdahoManager idahoManager = new IdahoManager();

        String tiffFileName = null;

        try {
            File outTiff = new File(System.getProperty("java.io.tmpdir") +
                    System.getProperty("file.separator") +
                    "out.tif");
            FileOutputStream fileOutputStream = new FileOutputStream(outTiff);

            tiffFileName = outTiff.getAbsolutePath();

            idahoManager.getTile("virtual-idaho", "96bbccc9-5a90-4a45-9846-adffc00a7387", "SourceImage", 1, 1, fileOutputStream);

            fileOutputStream.close();

            System.out.println( "saved file to " + tiffFileName );
        }
        catch( IOException fnfe ) {
            System.err.println( "error getting file " + tiffFileName );
            fnfe.printStackTrace();
        }
    }
}
