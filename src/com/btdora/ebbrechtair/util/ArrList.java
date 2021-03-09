package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.classes.Airport;
import com.btdora.ebbrechtair.classes.GeoCoordinate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArrList {

    public void getObjects() {

        SQLConnector sqlConnector = new SQLConnector();

        ArrayList<ArrayList<ArrayList<Airport>>> gridArray = new ArrayList<ArrayList<ArrayList<Airport>>>();


        //lon
        for (int i = 0; i < 180; i++) {
            gridArray.add(new ArrayList<ArrayList<Airport>>());
            for (int j = 0; j < 360; j++) {
                gridArray.get(i).add(new ArrayList<Airport>() );
            }
        }

        //gridArray.get(0).get(0).add(new Airport(2.,2.,"eddf"));
        gridArray.get(0).get(0).add(new Airport(2.,2.,"eddf"));


        try (Statement stmt = SQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * From db_Airport";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                int lat = (int)Math.floor(rs.getDouble("Lat"))+90;
                int lon = (int)Math.floor(rs.getDouble("Lon"))+180;


                //aktueller Airport
                //System.out.println(lat +" - "  + lon);
                gridArray.get(lat).get(lon).add(new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("AltitudeAirportInFeet"), rs.getString("a01"), rs.getString("a02"), rs.getInt("MaxRunwayLength"), rs.getString("b01")));





            }
            System.out.println(gridArray);
        } catch (SQLException e) {
            e.printStackTrace();
        }
     }
}


//gridArray.get(51).get(8).size();
// double lat = Math.floor(rs.getDouble("Lat"));
//double lon = Math.floor(rs.getDouble("Lon"));
// ArrayList<Airport> AirportArray = new ArrayList<>();
//     Airport airport = new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("AltitudeAirportInFeet"), rs.getString("a01"), rs.getString("a02"), rs.getInt("MaxRunwayLength"), rs.getString("b01"));
//   AirportArray.add(airport);


// System.out.println("Airport " + airport);
        // System.out.println("Lat: " + lat);
        //System.out.println("lon: " + lon);
        // System.out.println("----------");