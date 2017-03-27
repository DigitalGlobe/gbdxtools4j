package com.digitalglobe.gbdx.tools.idaho.model;

import java.util.Map;

/**
 * An Idaho Graph Node
 */
public class GraphNode {
    private String id;
    private String operator;
    private Map<String, String> parameters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public GraphNode withId(String id) {
        this.id = id;
        return this;
    }

    public GraphNode withOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public GraphNode withParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public String toString() {
        return "GraphNode {" +
                "id='" + id + '\'' +
                ", operator='" + operator + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
