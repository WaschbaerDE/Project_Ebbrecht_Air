package sample.util;

import sample.secret.SQLLoginData;

import java.sql.*;

public class SQLConnector {
    public static Connection getSQLConnection() throws SQLException{
        SQLLoginData sqlLoginData = new SQLLoginData();
        Connection connect = DriverManager.getConnection(sqlLoginData.getUrl(),sqlLoginData.getUserName(),sqlLoginData.getPassword());
        return connect;
    }
}
