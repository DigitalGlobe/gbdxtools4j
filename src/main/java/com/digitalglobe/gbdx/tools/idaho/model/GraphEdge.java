package com.digitalglobe.gbdx.tools.idaho.model;

/**
 * An Idaho Graph Edge
 */
public class GraphEdge {
    private String id;
    private Integer index;
    private String source;
    private String destination;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public GraphEdge withId(String id) {
        this.id = id;
        return this;
    }

    public GraphEdge withIndex(Integer index) {
        this.index = index;
        return this;
    }

    public GraphEdge withSource(String source) {
        this.source = source;
        return this;
    }

    public GraphEdge withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    @Override
    public String toString() {
        return "GraphEdge {" +
                "id='" + id + '\'' +
                ", index=" + index +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
