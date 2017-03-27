package com.digitalglobe.gbdx.tools.examples.idaho;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.digitalglobe.gbdx.tools.communication.ErrorMessage;
import com.digitalglobe.gbdx.tools.idaho.IdahoManager;
import com.digitalglobe.gbdx.tools.idaho.model.GraphEdge;
import com.digitalglobe.gbdx.tools.idaho.model.GraphNode;
import com.digitalglobe.gbdx.tools.idaho.model.IdahoGraph;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class StoreAndGetAndDeleteGraph {
    public static void main(String[] argv) {

        IdahoGraph idahoGraph = new IdahoGraph();
        GraphEdge graphEdge = new GraphEdge();
        graphEdge.withSource("read")
                .withDestination("crop")
                .withId(UUID.randomUUID().toString())
                .withIndex(1);
        idahoGraph.setEdges(Arrays.asList(graphEdge));
        GraphNode graphNode1 = new GraphNode();
        graphNode1.withId("read")
                .withOperator("IdahoRead");
        Map<String,String> parameters = new HashMap<>();
        parameters.put("bucketName", "idaho-images");
        parameters.put("imageId", "1e09507f-2b04-4827-9f96-5b6f2c15ab78");
        parameters.put("objectStore", "S3");
        graphNode1.setParameters(parameters);

        GraphNode graphNode2 = new GraphNode();
        graphNode2.withId("crop")
                .withOperator("Crop");
        parameters = new HashMap<>();
        parameters.put("x", "1000");
        parameters.put("width", "1000");
        parameters.put("y", "1000");
        parameters.put("height", "1000");
        graphNode2.setParameters(parameters);

        idahoGraph.setNodes(Arrays.asList(graphNode1, graphNode2));


        IdahoManager idahoManager = new IdahoManager();
        String graphId = null;

        try {
            graphId = idahoManager.storeGraph(idahoGraph);
        }
        catch( IOException ioe ) {
            System.err.println("got exception storing graph - " + ioe.getMessage() );
            System.exit(1);
        }

        System.out.println("got graph id " + graphId);

        try {
            IdahoGraph returnedGraph = idahoManager.getGraph(graphId);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String graphAsString = gson.toJson(returnedGraph);

            System.out.println("got graph of " + graphAsString);
        }
        catch( IOException ioe ) {
            System.err.println("got exception getting graph - " + ioe.getMessage() );
            System.exit(1);
        }

        try {
            ErrorMessage deleteResult = idahoManager.deleteGraph(graphId);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String deleteAsString = gson.toJson(deleteResult);

            System.out.println("delete result is " + deleteAsString);
        }
        catch( IOException ioe ) {
            System.err.println("got exception getting graph - " + ioe.getMessage() );
            System.exit(1);
        }

    }
}
