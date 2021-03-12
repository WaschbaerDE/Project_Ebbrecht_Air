package com.btdora.ebbrechtair.classes;

import java.sql.*;
import java.util.ArrayList;

public class Access {
    private Connection connection;

    public Access()
    {
        String url = "jdbc:sqlserver://provadis-it-ausbildung.de:1433;databaseName=userdb_03";
        String username = "dbUser_03";
        String password = "Q9X%qhu2knH";

        try
        {
            this.connection = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet fetchAll(String query) throws SQLException {
        return this.connection.createStatement().executeQuery(query);
    }

    public int update(String query) throws SQLException{
        return this.connection.createStatement().executeUpdate(query);
    }
}
