package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.secret.SQLLoginData;

import java.sql.*;

public class SQLConnector {
    public static Connection getSQLConnection() throws SQLException{
        SQLLoginData sqlLoginData = new SQLLoginData();
        Connection connect = DriverManager.getConnection(sqlLoginData.getUrl(),sqlLoginData.getUserName(),sqlLoginData.getPassword());
        return connect;
    }

//    public static Connection connector() throws SQLException {
//        String url = "jdbc:sqlserver://provadis-it-ausbildung.de:1433;databaseName=userdb_03";
//        String username = "dbUser_03";
//        String password = "Q9X%qhu2knH";
//
//        return DriverManager.getConnection(url, username, password);
//    }

    public static Statement getStatement() throws SQLException {
        return getSQLConnection().createStatement();
    }

    public static ResultSet fetchAll(String query) throws SQLException {
        return getStatement().executeQuery(query);
    }
}
