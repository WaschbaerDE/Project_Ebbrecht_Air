package com.btdora.ebbrechtair.util;

import java.sql.*;
import java.sql.Connection;


public class CheckIfStringIsValidAirportName {
    private String input;
    private String outputString;

    public String METHODE2(String input) {
        this.input = "Frankfurt";//get input1


        Connection c = Connection_SQL.connector();

        try (Statement stmt = c.createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE AirportName LIKE '%" + this.input + "%'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next() == false) {
                System.out.println("Wir konnten keinen Flughafen mit Ihrer Eingabe finden. Bitte geben Sie einen valide Eingabe ein.");
            }
            else{
                outputString = input;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }


        return outputString;
    }
}
