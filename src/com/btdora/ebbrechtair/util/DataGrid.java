package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.classes.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DataGrid {
    private final ArrayList<ArrayList<ArrayList<GeoCoordinate>>> gridArray = new ArrayList<ArrayList<ArrayList<GeoCoordinate>>>();

    // DataGrid = Funktion die das Karten-Raster befüllt
    public DataGrid() {
        for (int i = 0; i < 181; i++) {
            this.gridArray.add(new ArrayList<ArrayList<GeoCoordinate>>());
            for (int j = 0; j < 361; j++) {
                this.gridArray.get(i).add(new ArrayList<GeoCoordinate>());
            }
        }
        this.initializeAirports();
        this.initializeFixes();
        this.initializeNavaidsNDB();
        this.initializeNavaidsVOR();
        this.initializeNavaidsVORDME();
        this.initializeNavaidsDME();
    }

    //Befüllung des Rasters mit Flughafen-Objekten
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

    // Beffüllung des Rasters mit Fixes-Objekten
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

    //Befüllung des Rasters mit Navaids-Objekten
    private void initializeNavaidsNDB() {
        SQLConnector sqlConnector = new SQLConnector();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement()) {
            String SQL = "Select * From db_navaid über WHERE radialCapability = 0 AND dmeCapability= 0";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new Ndb(rs.getString("NavaidID"), rs.getString("NavaidName"), rs.getDouble("Frequency"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("Altitude"), rs.getString("AreaCode")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initializeNavaidsVOR() {
        SQLConnector sqlConnector = new SQLConnector();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement()) {
            String SQL = "Select * From db_navaid über WHERE radialCapability = 1 AND dmeCapability= 0";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new Vor(rs.getString("NavaidID"), rs.getString("NavaidName"), rs.getDouble("Frequency"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("Altitude"), rs.getString("AreaCode")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initializeNavaidsDME() {
        SQLConnector sqlConnector = new SQLConnector();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement()) {
            String SQL = "Select * From db_navaid über WHERE radialCapability = 0 AND dmeCapability= 1";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new Dme(rs.getString("NavaidID"), rs.getString("NavaidName"), rs.getDouble("Frequency"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("Altitude"), rs.getString("AreaCode")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initializeNavaidsVORDME() {
        SQLConnector sqlConnector = new SQLConnector();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement()) {
            String SQL = "Select * From db_navaid über WHERE radialCapability = 1 AND dmeCapability= 1";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                this.gridArray.get(lat).get(lon).add(new VorDme(rs.getString("NavaidID"), rs.getString("NavaidName"), rs.getDouble("Frequency"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("Altitude"), rs.getString("AreaCode")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Befüllung des Rasters mit Airway-Objekten
    private void initializeAirway() {
        SQLConnector sqlConnector = new SQLConnector();

        try (Statement stmt = SQLConnector.getSQLConnection().createStatement()) {
            String SQL = "Select * from db_Airway";        // SELECT-ABFRAGE Select * from db_Airport, db_Airway, db_Fix, db_Navaid, db_Runway
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                int lat = (int) Math.floor(rs.getDouble("Lat")) + 90;
                int lon = (int) Math.floor(rs.getDouble("Lon")) + 180;
                int LatNext = (int) Math.floor(rs.getDouble("LatNext")) + 90;
                int LonNext = (int) Math.floor(rs.getDouble("LonNext")) + 180;
                this.gridArray.get(lat).get(lon).add(new Airway(rs.getString("AirwayID"), rs.getInt("AirwayPartition"), rs.getString("FixID"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getString("IDOfNextFix"), rs.getDouble("LatNext"), rs.getDouble("LonNext"), rs.getInt("InBoundCourse"),rs.getInt("OutBoundCourse"),rs.getDouble("LegLength")));
                this.gridArray.get(LatNext).get(LonNext).add(new Airway(rs.getString("AirwayID"), rs.getInt("AirwayPartition"), rs.getString("FixID"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getString("IDOfNextFix"), rs.getDouble("LatNext"), rs.getDouble("LonNext"), rs.getInt("InBoundCourse"),rs.getInt("OutBoundCourse"),rs.getDouble("LegLength")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<GeoCoordinate> get(int lat, int lon) {
        return this.gridArray.get(lat+90).get(lon+180);
    }
}
