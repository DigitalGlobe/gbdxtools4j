package com.digitalglobe.gbdx.tools.workflow.model;

import java.util.List;

import com.google.gson.Gson;

/**
 * Simple definition of a workflow list
 */
public class WorkflowList {
    private List<String> workflows;

    public List<String> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(List<String> workflows) {
        this.workflows = workflows;
    }

    public WorkflowList withWorkflows(List<String> workflows) {
        this.workflows = workflows;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
