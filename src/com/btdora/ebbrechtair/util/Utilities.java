package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.classes.Airport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Utilities = Überprüfung der Eingabe von Start- und Ziel-Flughäfen
public class Utilities {
    
    //Checkt ob der eingegebene String ein ICAO-Code ist
    public static List<Airport> getIcaoByName(String input) {
        List<Airport> list = new ArrayList<Airport>();
        SQLConnector createSQLConnector = new SQLConnector();

        try (Statement stmt = createSQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE ICAOCode LIKE '" + input + "'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                list.add(new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("AltitudeAirportInFeet"), rs.getInt("MaxRunwayLength"), rs.getInt("IFR")));
            } else {
                list = getAirportByName(input);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Checkt ob der eingegebene String ein Flughafen ist
    public static List<Airport> getAirportByName(String input) {
        List<Airport> list = new ArrayList<Airport>();
        SQLConnector createSQLConnector = new SQLConnector();

        try (Statement stmt = createSQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE AirportName LIKE '%" + input + "%'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);

            while(rs.next()) {
                list.add(new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("AltitudeAirportInFeet"), rs.getInt("MaxRunwayLength"), rs.getInt("IFR")));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
