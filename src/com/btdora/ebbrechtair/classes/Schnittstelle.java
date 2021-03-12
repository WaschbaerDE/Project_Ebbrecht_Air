package com.btdora.ebbrechtair.classes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Schnittstelle {

    public static Route routeFinder(String depAirport, String arrAirport) throws SQLException {
        DatabaseReader2 databaseReader2 = new DatabaseReader2();
        HashMap<String, DijkstraNode> waypoints = databaseReader2.getAllWaypoints();
        DijkstraNode comparator = new DijkstraNode(0, null);
        ArrayList<DijkstraNode> shortestPath = new ArrayList<>();

        for (String startKey : Airport.departureAirport("EDDF")) {

            if (waypoints.containsKey(startKey)) {
                Dijkstra dijkstraNode = null;
                dijkstraNode = new Dijkstra();
                dijkstraNode.sidNode = waypoints.get(startKey);

                for (String endKey : Airport.arrivalAirport("EDDH")) {

                    if (waypoints.containsKey(endKey)) {

                        dijkstraNode.starNode = waypoints.get(endKey);
                        List<DijkstraNode> path = dijkstraNode.findRoute(dijkstraNode.sidNode, dijkstraNode.starNode);

                        for (DijkstraNode dijkstraNode1 : path) {

                            if (path.get(path.size() - 1).getDistance() < comparator.getDistance()) {
                                shortestPath.clear();
                                comparator.setDistance(path.get(path.size() - 1).getDistance());
                                shortestPath.addAll(path);
                            }
                        }
                    }
                }
                waypoints.forEach((k, v) -> {
                    v.setVisited(false);
                    v.setPredecessor(null);
                    v.setDistance(Double.MAX_VALUE);
                });
            }
        }

//        String lastWaypoint = shortestPath.get(shortestPath.size()-1).getName();
//
//
        Double distance = shortestPath.get(shortestPath.size()-1).getDistance();
//        Double actualDistance = distance + getSidDistance(shortestPath.get(0).getName()) +
//                getStarDistance(shortestPath.get(shortestPath.size()-1).getName());
//        DijkstraNode firstAirport = databaseReader2.getAirportInfo(firstAirport);
//        DijkstraNode secondAirport = databaseReader2.getAirportInfo(secondAirport);
//        shortestPath.add(0, firstAirport);
//        shortestPath.add(shortestPath.size()-1, secondAirport);
        Route route = new Route(shortestPath, distance);
        for (DijkstraNode d : shortestPath
             ) {
            System.out.println(d.getName());
        }
        return route;

    }
}
