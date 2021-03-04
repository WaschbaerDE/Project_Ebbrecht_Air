package com.company;

import com.btdora.ebbrechtair.util.CheckIfStringIsValidAirportName;

import java.sql.*;
import java.sql.Connection;


public class CheckIfStringIsValidICAO {
    private String input;
    private String outputString;


    public String METHODE1(String input) {
        this.input = "Frankfurt";//get input1


        Connection c = Connection_SQL.connector();

        try (Statement stmt = c.createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE ICAOCode LIKE '%" + this.input + "%'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next() == false) {
                CheckIfStringIsValidAirportName checkIfStringIsValidAirportName = new CheckIfStringIsValidAirportName();
                checkIfStringIsValidAirportName.METHODE2(this.input);
            }

            else{
                outputString = this.input;
            }
        }
        catch(SQLException e){
                e.printStackTrace();
            }


        return outputString;
    }
}
