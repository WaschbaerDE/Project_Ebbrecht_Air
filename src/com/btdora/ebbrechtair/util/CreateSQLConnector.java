package com.btdora.ebbrechtair.util;

import com.btdora.ebbrechtair.secrets.SQLLoginData;

import java.sql.*;

public class CreateSQLConnector {
    public static Connection getSQLConnection() throws SQLException{
        SQLLoginData sqlLoginData = new SQLLoginData();
        Connection connect = DriverManager.getConnection(sqlLoginData.getUrl(),sqlLoginData.getUserName(),sqlLoginData.getPassword());
        return connect;
    }
}
