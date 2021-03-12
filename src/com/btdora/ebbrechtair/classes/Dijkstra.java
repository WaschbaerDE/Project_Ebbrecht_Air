package com.btdora.ebbrechtair.classes;

import java.util.*;

public class Dijkstra {

    DijkstraNode sidNode;
    DijkstraNode starNode;
    ArrayList<List> test = new ArrayList<>();

    public List<DijkstraNode> findRoute(DijkstraNode sidNode, DijkstraNode starNode) {
        this.computeShortestPaths(sidNode);
        return this.getShortestPathTo(starNode);
    }

    public void computeShortestPaths(DijkstraNode sourceNode){
        this.sidNode = sourceNode;
        this.sidNode.setDistance(0);
        PriorityQueue<DijkstraNode> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(this.sidNode);
        this.sidNode.setVisited(true);

        while( !priorityQueue.isEmpty() ){
            // Getting the minimum distance node from priority queue
            DijkstraNode actualNode = priorityQueue.poll();

            for(Edge edge : actualNode.getAdjacenciesList()){

                DijkstraNode v = edge.getTargetNode();
                if(!v.isVisited())
                {
                    double newDistance = actualNode.getDistance() + edge.getDistance();

                    if( newDistance < v.getDistance() ){
                        priorityQueue.remove(v);
                        v.setDistance(newDistance);
                        v.setPredecessor(actualNode);
                        priorityQueue.add(v);
                    }
                }

            }
            actualNode.setVisited(true);
        }
        priorityQueue.clear();
    }

    public List<DijkstraNode> getShortestPathTo(DijkstraNode starNode){
        //this.starNode = starNode;
        List<DijkstraNode> path = new ArrayList<>();

        for(DijkstraNode node=starNode; node!=null; node=node.getPredecessor()){
            path.add(node);
        }

        Collections.reverse(path);
        return path;
    }
}
