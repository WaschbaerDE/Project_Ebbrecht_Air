package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.classes.Airport;
import com.btdora.ebbrechtair.classes.GeoCoordinate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ArrList {


    //ArrayList <Airport>[][] airPortList = new ArrayList [360][180];

    public void getObjects() {

        SQLConnector sqlConnector = new SQLConnector();

        ArrayList<ArrayList<Airport>> geoGrid = new ArrayList<ArrayList<Airport>>();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                Airport airport = new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("AltitudeAirportInFeet"), rs.getString("a01"), rs.getString("a02"), rs.getInt("MaxRunwayLength"), rs.getString("b01")));
                double lat = Math.floor(rs.getDouble("Lat"));
                double lon = Math.floor(rs.getDouble("Lon"));



                System.out.println(airPortList[0][0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        }
    }


