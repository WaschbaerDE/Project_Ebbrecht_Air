package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.classes.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataGrid {

    private ArrayList<ArrayList<ArrayList<GeoCoordinate>>> gridArray = new ArrayList<ArrayList<ArrayList<GeoCoordinate>>>();

    public DataGrid() {

        SQLConnector sqlConnector = new SQLConnector();

        //lon
        for (int i = 0; i < 180; i++) {
            this.gridArray.add(new ArrayList<ArrayList<GeoCoordinate>>());
            for (int j = 0; j < 360; j++) {
                this.gridArray.get(i).add(new ArrayList<GeoCoordinate>());
            }
        }

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement();) {
            String SQL = "Select * from db_Airport";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("AltitudeAirportInFeet"), rs.getString("a01"), rs.getString("a02"), rs.getInt("MaxRunwayLength"), rs.getString("b01")));

            }
            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new Fix(rs.getString("FixID"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getString("Areacode"), rs.getString("Departure"), rs.getString("Arrival")));
            }

            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new Navaid(rs.getString("NavaidID"), rs.getString("NavaidName"), rs.getDouble("Frequency"), rs.getInt("RadialCapability"), rs.getInt("DMECapability"), rs.getString("a01"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("Altitude"), rs.getString("AreaCode"), rs.getString("a02")));

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GeoCoordinate> get(int lat, int lon) {
        return this.gridArray.get(lat+90).get(lon+180);
    }
}

