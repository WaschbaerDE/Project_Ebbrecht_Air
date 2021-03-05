package com.btdora.ebbrechtair.util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckIfStringIsValidAirportName {
    private String input;
    private String outputString;
    List<Airport> list = new ArrayList<Airport>();
    public String CheckIfStringIsValidAirportName(String input) {
        this.input = input;
        SQLConnector createSQLConnector = new SQLConnector();

        try (Statement stmt = createSQLConnector.getSQLConnection().createStatement();) {
            String SQL = "SELECT * FROM db_Airport WHERE AirportName LIKE '%" + this.input + "%'";        // SELECT-ABFRAGE
            ResultSet rs = stmt.executeQuery(SQL);
            if (rs.next() ==  false) {
                System.out.println("Wir konnten keinen Flughafen mit Ihrer Eingabe finden. Bitte geben Sie einen valide Eingabe ein.");



            }
            else{
                while(rs.next()){
                    list.add(new Airport(rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getString(8));

                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return this.outputString;
    }
}
