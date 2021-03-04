package sample.secret;

import sample.util.SQLConnector;

import sample.util.SQLConnector;

import java.sql.Connection;

public class SQLLoginData{
    private String url = "jdbc:sqlserver://provadis-it-ausbildung.de:1433;databaseName=userdb_03";
    private String userName = "dbUser_03";
    private String password = "Q9X%qhu2knH";

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}