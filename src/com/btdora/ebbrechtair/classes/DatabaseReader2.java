package com.btdora.ebbrechtair.classes;

import com.btdora.ebbrechtair.util.SQLConnector;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class DatabaseReader2 {

    private final Access access = new Access();
    private final HashMap<String, DijkstraNode> dijkstraNodes = new HashMap<String, DijkstraNode>();
    private final HashMap<String, ArrayList<Edge>> legs = new HashMap<String, ArrayList<Edge>>();

    private void initializeWaypoints() throws SQLException {
        String query = "SELECT waypoints.*, Frequency\n" +
                "FROM waypoints\n" +
                "LEFT JOIN db_Navaid\n" +
                "\tON\n" +
                "\t\twaypointName = NavaidID AND\n" +
                "\t\tlongitude = Lon AND\n" +
                "\t\tlatitude = Lat";

        ResultSet resultSet = this.access.fetchAll(query);

        while (resultSet.next())
        {
            dijkstraNodes.put(resultSet.getString("waypointName") + resultSet.getDouble("longitude") + resultSet.getDouble("latitude"), new DijkstraNode(resultSet.getInt("waypointId"), resultSet.getString("waypointname"), resultSet.getDouble("longitude"), resultSet.getDouble("latitude"), resultSet.getDouble("Frequency")));
        }
    }

    private void initializeLegs() throws SQLException {
        String query = "SELECT * FROM db_Airway;";

        ResultSet resultSet = this.access.fetchAll(query);

        while (resultSet.next())
        {
            String key = resultSet.getString("FixID") + resultSet.getDouble("Lon") + resultSet.getDouble("Lat");
            String keyNext = resultSet.getString("IDOfNextFix") + resultSet.getDouble("LonNext") + resultSet.getDouble("LatNext");

            DijkstraNode fix = this.dijkstraNodes.get(key);
            DijkstraNode fixNext = this.dijkstraNodes.get(keyNext);

            if (!this.legs.containsKey(key)) {
                this.legs.put(key, new ArrayList<Edge>());
            }
            this.legs.get(key).add(new Edge(fix, fixNext, resultSet.getDouble("LegLength"), resultSet.getString("AirwayID"), resultSet.getInt("OutBoundCourse")));
        }
    }

    public HashMap<String, DijkstraNode> getAllWaypoints() throws SQLException {
        this.initializeWaypoints();
        this.initializeLegs();

        dijkstraNodes.forEach((key, dijkstraNode) -> {
            //System.out.println("Current Node: " + dijkstraNode.getName());

            if(this.legs.containsKey(key))
            {
                for (Edge connection : this.legs.get(key))
                {
                    dijkstraNode.addNeighbour(connection);
                }
            }
        });

//        for (DijkstraNode dijkstraNode : dijkstraNodes)
//        {
//            if (dijkstraNode.getLongitude() >= 2.352222 && dijkstraNode.getLongitude() <= 14.43780 && dijkstraNode.getLatitude() >= 46.16700 && dijkstraNode.getLongitude() <= 50.110922)
//            {
//                System.out.println("Current Node: " + dijkstraNode.getName());
//
//                for (Edge connection : getConnections(dijkstraNode))
//                {
//                    dijkstraNode.addNeighbour(connection);
//                    dijkstraNodesWithKey.get(dijkstraNode.getName() + dijkstraNode.getLongitude() + dijkstraNode.getLatitude()).addNeighbour(connection);
//                }
//            }
//        }
        return dijkstraNodes;
    }

    public HashMap<String, DijkstraNode> getDijkstraNodesMap() {
        return this.dijkstraNodes;
    }
    public DijkstraNode getAirportInfo(String airport) throws SQLException {
        DijkstraNode airportInfo = new DijkstraNode();
        String query = "SELECT *\n" +
                "FROM db_Airport\n" +
                "WHERE ICAOCode = '" + airport + "'";
        ResultSet rs = SQLConnector.fetchAll(query);
        while (rs.next()) {
            airportInfo = new DijkstraNode(rs.getString("ICAOCode"),
                    rs.getDouble("Lon"), rs.getDouble("Lat"));
        }
        return airportInfo;
    }


}