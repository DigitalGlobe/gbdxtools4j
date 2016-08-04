package com.digitalglobe.gbdx.tools.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Simple container for error messages.
 */
public class ErrorMessage {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }
}
