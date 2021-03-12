package com.btdora.ebbrechtair.classes;

import java.util.*;

public class DijkstraNode implements Comparable<DijkstraNode> {
    private int id;
    private String name;
    private List<Edge> adjacenciesList;
    private boolean visited;
    private DijkstraNode predecessor;
    private double frequency = Double.MAX_VALUE;
    private double longitude;
    private double latitude;
    private double distance = Double.MAX_VALUE;

    public DijkstraNode() {}

    public DijkstraNode(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public DijkstraNode(String name, Double longitude, Double latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public DijkstraNode(int id, String name, double longitude, double latitude, double frequency) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.frequency = frequency;
        this.adjacenciesList = new ArrayList<>();
    }

    public void addNeighbour(Edge edge) {
        this.adjacenciesList.add(edge);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Edge> getAdjacenciesList() {
        return adjacenciesList;
    }

    public void setAdjacenciesList(List<Edge> adjacenciesList) {
        this.adjacenciesList = adjacenciesList;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public DijkstraNode getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(DijkstraNode predecessor) {
        this.predecessor = predecessor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DijkstraNode that = (DijkstraNode) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(DijkstraNode otherNode) {
        return Double.compare(this.distance, otherNode.getDistance());
    }
}
