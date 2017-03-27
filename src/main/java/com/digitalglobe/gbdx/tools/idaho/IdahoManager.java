package com.digitalglobe.gbdx.tools.idaho;

import java.io.IOException;
import java.io.OutputStream;

import com.digitalglobe.gbdx.tools.communication.CommunicationBase;
import com.digitalglobe.gbdx.tools.communication.ErrorMessage;
import com.digitalglobe.gbdx.tools.idaho.model.IdahoGraph;
import com.google.gson.Gson;


/**
 * Manages the interface with the IDAHO system.
 */
public class IdahoManager extends CommunicationBase {

    private static String baseUrl;


    public IdahoManager() {
        baseUrl = configurationManager.getBaseAPIUrl() + "/v1";
    }

    /**
     * Calls /v1/heartbeat to check if the idaho system is alive
     *
     * @return true if we get an "ok", false otherwise.
     *
     * @throws IOException if there is an error communicating
     *
     */
    public boolean isAlive() throws IOException {
        String okString = getData(baseUrl + "/heartbeat", false);

        if (okString != null) {
            if ((okString.trim().toLowerCase().contains("ok")))
                return true;
        }

        return false;
    }


    /**
     * Calls /v1/graph to store a new graph
     *
     * @param graph the graph to store
     *
     * @return the graph id returned from the service
     *
     * @throws IOException if there is an error communicating
     *
     */
    public String storeGraph(IdahoGraph graph) throws IOException {
        return postData(baseUrl + "/graph", new Gson().toJson(graph), true);
    }

    /**
     * Calls /v1/{graphId} to get a graph
     *
     * @param graphId the id of the graph to return
     *
     * @return the graph id returned from the service
     *
     * @throws IOException if there is an error communicating
     *
     */
    public IdahoGraph getGraph(String graphId) throws IOException {
        String graphBody = getData(baseUrl + "/graph/" + graphId,true);

        return new Gson().fromJson(graphBody, IdahoGraph.class);
    }

    /**
     * Calls /v1/{graphId} to delete a graph
     *
     * @param graphId the id of the graph to delete
     **
     * @throws IOException if there is an error communicating
     *
     */
    public ErrorMessage deleteGraph(String graphId) throws IOException {
        return delete(baseUrl + "/graph/" + graphId,true);
    }

    public void getTile(String bucketId, String graphId, String nodeId, int x, int y, OutputStream outputStream) throws IOException {
        getData(baseUrl + "/tile/" + bucketId + "/" + graphId + "/" + nodeId + "/" + x + "/" + y + ".tif",
                outputStream, true );
    }
}