package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.classes.Airport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Utilities {


    public static List<Airport> getIcaoByName(String input) {
        List<Airport> list = new ArrayList<Airport>();

        SQLConnector createSQLConnector = new SQLConnector();

        try (Statement stmt = createSQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE ICAOCode LIKE '" + input + "'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next()) {
                list.add(new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getInt("AltitudeAirportInFeet"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("MaxRunwayLength")));
            } else {
                list = getAirportByName(input);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Airport> getAirportByName(String input) {
        List<Airport> list = new ArrayList<Airport>();
        SQLConnector createSQLConnector = new SQLConnector();

        try (Statement stmt = createSQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE AirportName LIKE '%" + input + "%'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);

            while(rs.next()) {
                list.add(new Airport(rs.getString("ICAOCode"), rs.getString("AirportName"), rs.getInt("AltitudeAirportInFeet"), rs.getDouble("Lat"), rs.getDouble("Lon"), rs.getInt("MaxRunwayLength")));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void stringCheck(String inputStart, String inputZiel){

        




    }



}