package com.digitalglobe.gbdx.tools.orders.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Container for order results.
 */
public class OrderResult {

    @SerializedName("order_id")
    @Expose
    private String orderId;

    @Expose
    private String callback;

    @Expose
    private List<Acquisition> acquisitions;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }

    private static class Acquisition {

        public Acquisition(String acqId, String state, String location ) {
            this.acquisitionId =  acqId;
            this.state = state;
            this.location = location;
        }

        @SerializedName("acquisition_id")
        @Expose
        private String acquisitionId;

        @Expose
        private String state;

        @Expose
        private String location;

        @Override
        public String toString() {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            return gson.toJson(this);
        }
    }
}
