package com.btdora.ebbrechtair.secret;


public class SQLLoginData{
    private final String url = "jdbc:sqlserver://provadis-it-ausbildung.de:1433;databaseName=userdb_03";
    private final String userName = "dbUser_03";
    private final String password = "Q9X%qhu2knH";

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