package com.btdora.ebbrechtair.util;

import java.sql.*;

public class Utilities {
    private String input;
    private String outputString;

    public String getIcaoByName(String input) {

        this.input = input;
        SQLConnector createSQLConnector = new SQLConnector();

        try (Statement stmt = createSQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE ICAOCode LIKE '" + this.input + "'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next() == false) {
                CheckIfStringIsValidAirportName checkIfStringIsValidAirportName = new CheckIfStringIsValidAirportName();
                checkIfStringIsValidAirportName.CheckIfStringIsValidAirportName(this.input);
            }
            else{
                this.outputString = this.input;     //Hier wird aus dem ICAO Code eine Airport generiert. Methode muss noch geschrieben werden;
            }
        }
        catch(SQLException e){
                e.printStackTrace();
            }
        return this.outputString;
    }
}
