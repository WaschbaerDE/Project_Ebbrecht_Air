package com.btdora.ebbrechtair.classes;

public class Edge {

    private DijkstraNode startNode;
    private DijkstraNode targetNode;
    private double distance;
    private String airway;
    private int bearing;

    public Edge(DijkstraNode startNode, DijkstraNode targetNode, double distance, String airway, int bearing) {
        this.startNode = startNode;
        this.targetNode = targetNode;
        this.distance = distance;
        this.airway = airway;
        this.bearing = bearing;
    }

    public DijkstraNode getStartNode() {
        return startNode;
    }

    public void setStartNode(DijkstraNode startNode) {
        this.startNode = startNode;
    }

    public DijkstraNode getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(DijkstraNode targetNode) {
        this.targetNode = targetNode;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getAirway() {
        return airway;
    }

    public void setAirway(String airway) {
        this.airway = airway;
    }

    public int getBearing() {
        return bearing;
    }

    public void setBearing(int bearing) {
        this.bearing = bearing;
    }
}