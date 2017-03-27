package com.digitalglobe.gbdx.tools.idaho.model;

import java.util.List;

/**
 * Representation of an Idaho Graph
 */
public class IdahoGraph {
    private String id;
    private List<GraphEdge> edges;
    private List<GraphNode> nodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<GraphEdge> edges) {
        this.edges = edges;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public IdahoGraph withId(String id) {
        this.id = id;
        return this;
    }

    public IdahoGraph withEdges(List<GraphEdge> edges) {
        this.edges = edges;
        return this;
    }

    public IdahoGraph withNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
        return this;
    }

    @Override
    public String toString() {
        return "IdahoGraph {" +
                "id='" + id + '\'' +
                ", edges=" + edges +
                ", nodes=" + nodes +
                '}';
    }

}
