package com.digitalglobe.gbdx.tools.orders.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Simple container for requesting an order.
 */
public class OrderRequest {
    @Expose
    private List<String> acquisitionIds;

    private String callback;

    public OrderRequest( List<String> acquisitionIds ) {
        this.acquisitionIds = acquisitionIds;
    }

    public OrderRequest( List<String> acquisitionIds, String callback ) {
        this.acquisitionIds = acquisitionIds;
        this.callback = callback;
    }

    public OrderRequest withAcquisitionId( String acquisitionId ) {
        if( acquisitionIds == null )
            acquisitionIds = new ArrayList<>();

        acquisitionIds.add(acquisitionId);

        return this;
    }

    public String getCallback() {
        return callback;
    }

    public List<String> getAcquisitionIds() {
        return acquisitionIds;
    }



    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }
}
