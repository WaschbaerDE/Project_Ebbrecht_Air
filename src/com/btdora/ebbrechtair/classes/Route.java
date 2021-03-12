package com.btdora.ebbrechtair.classes;

import java.util.ArrayList;

public class Route {
    public double distance;
    public ArrayList<DijkstraNode> path;


    public Route(ArrayList<DijkstraNode> shortestPath, Double distance) {
    }

    public double getDistance() {
        return distance;
    }

    public ArrayList<DijkstraNode> getPath() {
        return path;
    }
}
