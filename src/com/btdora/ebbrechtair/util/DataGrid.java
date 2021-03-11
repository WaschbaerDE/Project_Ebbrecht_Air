package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.classes.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataGrid {
    private final ArrayList<ArrayList<ArrayList<GeoCoordinate>>> gridArray = new ArrayList<ArrayList<ArrayList<GeoCoordinate>>>();

    public DataGrid() {
        for (int i = 0; i < 181; i++) {
            this.gridArray.add(new ArrayList<ArrayList<GeoCoordinate>>());
            for (int j = 0; j < 361; j++) {
                this.gridArray.get(i).add(new ArrayList<GeoCoordinate>());
            }
        }
        this.initializeAirports();
        this.initializeFixes();
        this.initializeNavaids();
    }

    private void initializeAirports() {

        SQLConnector sqlConnector = new SQLConnector();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement()) {
            String SQL = "Select * from db_Airport";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("AltitudeAirportInFeet"), rs.getInt("MaxRunwayLength"), rs.getInt("IFR")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeFixes() {
        SQLConnector sqlConnector = new SQLConnector();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement()) {
            String SQL = "Select * from db_Fix";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new Fix(rs.getString("FixID"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getString("Areacode"), rs.getString("Departure"), rs.getString("Arrival")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initializeNavaids() {
        SQLConnector sqlConnector = new SQLConnector();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement()) {
            String SQL = "Select * from db_Navaid";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                //this.gridArray.get(lat).get(lon).add(new Fix(rs.getString("NavaidID"), rs.getString("NavaidName"), rs.getDouble("Frequency"), rs.getInt("RadialCapability"), rs.getInt("DMECapability"), rs.getString("a01"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("Altitude"), rs.getString("AreaCode"), rs.getString("a02")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GeoCoordinate> get(int lat, int lon) {
        return this.gridArray.get(lat+90).get(lon+180);
    }
}
