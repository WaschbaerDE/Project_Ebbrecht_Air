package com.btdora.ebbrechtAir.util;

import java.sql.*;

public class CheckIfStringIsValidAirportName {
    private String input;
    private String outputString;

    public String CheckIfStringIsValidAirportName(String input) {
        this.input = "Frankfurt";//get input1
        SQLConnector createSQLConnector = new SQLConnector();

        try (Statement stmt = createSQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE AirportName LIKE '%" + this.input + "%'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next() == false) {
                System.out.println("Wir konnten keinen Flughafen mit Ihrer Eingabe finden. Bitte geben Sie einen valide Eingabe ein.");
            }
            else{
                this.outputString = input;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return this.outputString;
    }
}
