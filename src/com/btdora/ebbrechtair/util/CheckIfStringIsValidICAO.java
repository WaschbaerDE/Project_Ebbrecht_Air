package com.btdora.ebbrechtair.util;

import java.sql.*;

public class CheckIfStringIsValidICAO {
    private String input;
    private String outputString;

    public String checkIfStringIsValidICAO(String input) {

        this.input = "Frankfurt";//get input1//Platzhalterbewirtschaftung
        CreateSQLConnector createSQLConnector = new CreateSQLConnector();

        try (Statement stmt = createSQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE ICAOCode LIKE '%" + this.input + "%'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next() == false) {
                CheckIfStringIsValidAirportName checkIfStringIsValidAirportName = new CheckIfStringIsValidAirportName();
                checkIfStringIsValidAirportName.CheckIfStringIsValidAirportName(this.input);
            }
            else{
                this.outputString = this.input;
            }
        }
        catch(SQLException e){
                e.printStackTrace();
            }
        return this.outputString;
    }
}
