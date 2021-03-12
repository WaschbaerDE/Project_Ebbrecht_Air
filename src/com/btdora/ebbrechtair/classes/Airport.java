package com.btdora.ebbrechtair.classes;

import com.btdora.ebbrechtair.util.SQLConnector;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Airport extends GeoCoordinate {
    private String icaoCode;

    private final String icaoCode;
    private String airportName;
    private int altitudeAirportInFeet;
    private int maxRunwayLength;
    private int ifr;

    double latPlus;
    double latMinus;
    double lonPlus;
    double lonMinus;

    ArrayList<Sid> sidList = new ArrayList<>();
    ArrayList<String> sidListStrings = new ArrayList<>();
    ArrayList <Star> starList = new ArrayList<>();
    ArrayList<String> starListStrings = new ArrayList<>();
    ArrayList <Approach> approachList = new ArrayList<>();
    ArrayList <Final> finalList = new ArrayList<>();


    //full constructor for the creation of an Airport
    public Airport(String icaoCode, String airportName, double lat, double lon, int altitudeAirportInFeet, int maxRunwayLength, int ifr) {
        super(lat, lon);
        this.icaoCode = icaoCode;
        this.airportName = airportName;
        this.altitudeAirportInFeet = altitudeAirportInFeet;
        this.maxRunwayLength = maxRunwayLength;
        this.ifr = ifr;
        this.latPlus = this.getLat();
        this.latMinus = this.getLat();
        this.lonPlus = this.getLon();
        this.lonMinus = this.getLon();
    }

    public Airport() {

    }
    
    //test-data constructor
    public Airport(String icaoCode, Double lat, Double lon) {
        super(lat, lon);
        this.icaoCode = icaoCode;
    }

    public String toString() {
        return this.icaoCode+" "+this.airportName;
    }

    public int getifr() {
        return ifr;
    }

    public void setifr(int ifr) {
        this.ifr = ifr;
    }



    public static ArrayList<String> departureAirport(String startAirport) throws SQLException {
        ArrayList<String> returnKeys = new ArrayList<>();
        ArrayList<String> airportsWithoutSids = Airport.getAirportsWithoutSids();
        Airport airportObj = new Airport();
//        airportObj.fillLatLon();
        airportObj.airportName = startAirport.toUpperCase();
        airportObj.fillAirport();
        if(airportsWithoutSids.contains(airportObj.airportName)){
            airportObj.getNextFixSid();
        }else {
            airportObj.getSids();

        }
        for (Sid s : airportObj.sidList
        ) {

            returnKeys.add(s.sidId + s.getLon() + s.getLat());
        }
        return returnKeys;
    }

    public static ArrayList<String> arrivalAirport(String endAirport) throws SQLException {
        ArrayList<String> returnKeys = new ArrayList<>();
        Airport airportObj = new Airport();
        airportObj.airportName = endAirport.toUpperCase();
        ArrayList <String> airportsWithoutStars = Airport.getAirportsWithoutStars();
        ArrayList <String> airportsWithoutStarsAndApproaches = Airport.getAirportsWithoutStarsAndApproaches();
        ArrayList <String> airportsWithoutStarsAndApproachesAndFinals = Airport.getAirportsWithoutStarsAndApproachesAndFinals();
        airportObj.fillAirport();
        if((airportsWithoutStars.contains(airportObj.airportName)) && !(airportsWithoutStarsAndApproaches.contains(airportObj.airportName))){
            airportObj.getApproaches();
        } else if(airportsWithoutStarsAndApproaches.contains(airportObj.airportName) && !(airportsWithoutStarsAndApproachesAndFinals.contains(airportObj.airportName))) {
            airportObj.getFinals();

        } else if(airportsWithoutStarsAndApproachesAndFinals.contains(airportObj.airportName)){
            airportObj.getNextFixStar();
        } else {
            airportObj.getStars();

        }
        for (Star s: airportObj.starList
        ) {
            returnKeys.add(s.starId + s.getLon()  + s.getLat());
        }
        return returnKeys;
    }

    public void fillAirport() throws SQLException {
        String query = "select air.ICAOCode,\n" +
                "air.Lat, \n" +
                "air.Lon  \n" +
                "from dbo.db_Airport as air\n" +
                "where air.ICAOCode = '"+this.airportName+"'\n" +
                ";";
        ResultSet allwaypointsWithConnectionAirport = SQLConnector.fetchAll(query);
        allwaypointsWithConnectionAirport.next();
        this.setLat(allwaypointsWithConnectionAirport.getDouble("lat"));
        this.setLon(allwaypointsWithConnectionAirport.getDouble("lon"));
        fillLatLon();
    }

    public void getSids() throws SQLException {
        String query = "with cte_Sid\n" +
                "as(\n" +
                "select sid.SidId from dbo.Sid as sid\n" +
                "inner join db_Airport as air\n" +
                "on sid.iCAOCode = air.ICAOCode \n" +
                "where sid.iCAOCode = '"+this.airportName+"'\n" +
                "group by SidId),\n" +
                "\n" +
                "cte_Airway\n" +
                "as(select aw.FixId from db_Airway as aw\n" +
                "),\n" +
                "\n" +
                "cte_all \n" +
                "as\n" +
                "(\n" +
                "select aw.FixID, si.SidId from cte_Airway as aw\n" +
                "left join cte_Sid as si on si.SidId = aw.FixID\n" +
                " \n" +
                ")\n" +
                "\n" +
                "select distinct * from cte_all\n" +
                "where cte_all.SidId is not null\n" +
                ";";

        ResultSet allwaypointsWithConnection = SQLConnector.fetchAll(query);

        ArrayList <String> arrayString = new ArrayList<>();
        while (allwaypointsWithConnection.next()){
            arrayString.add(allwaypointsWithConnection.getString("FixId"));
        }
        for(String s : arrayString){
            String query2 = "select sid.SidId, sid.Lat, sid.Lon from dbo.Sid as sid\n" +
                    "inner join db_Airport as air\n" +
                    "on sid.iCAOCode = air.ICAOCode\n" +
                    "where sid.iCAOCode = '"+this.airportName+"' and Waypoint = '"+s+"'\n" +
                    ";";
            ResultSet allwaypointsWithConnection2 = SQLConnector.fetchAll(query2);
            allwaypointsWithConnection2.next();
            Sid sidobj = new Sid();
            sidobj.sidId = allwaypointsWithConnection2.getString("SidId");
            sidobj.setLat(allwaypointsWithConnection2.getDouble("Lat"));
            sidobj.setLon(allwaypointsWithConnection2.getDouble("Lon"));
            this.sidList.add(sidobj);
        }
    }

    public void getStars () throws SQLException {
        String query = "with cte_Star\n" +
                "as(\n" +
                "select star.StarId from dbo.Star as star\n" +
                "inner join db_Airport as air\n" +
                "on star.iCAOCode = air.ICAOCode \n" +
                "where star.iCAOCode = '"+this.airportName+"'\n" +
                "group by StarId),\n" +
                "\n" +
                "cte_Airway\n" +
                "as(select aw.FixId from db_Airway as aw\n" +
                "),\n" +
                "\n" +
                "cte_all \n" +
                "as\n" +
                "(\n" +
                "select aw.FixID, st.StarId from cte_Airway as aw\n" +
                "left join cte_Star as st on st.StarId = aw.FixID\n" +
                " \n" +
                ")\n" +
                "\n" +
                "select distinct * from cte_all\n" +
                "where cte_all.StarId is not null\n" +
                ";";

        ResultSet allwaypointsWithConnection = SQLConnector.fetchAll(query);

        ArrayList <String> arrayString = new ArrayList<>();
        while (allwaypointsWithConnection.next()){
            arrayString.add(allwaypointsWithConnection.getString("FixId"));
        }
        for(String s : arrayString){
            String query2 = "select star.StarId, star.Lat, star.Lon from dbo.Star as star\n" +
                    "inner join db_Airport as air\n" +
                    "on star.iCAOCode = air.ICAOCode\n" +
                    "where star.iCAOCode = '"+this.airportName+"' and Waypoint = '"+s+"'\n" +
                    ";";
            ResultSet allwaypointsWithConnection2 = SQLConnector.fetchAll(query2);
            allwaypointsWithConnection2.next();
            Star starobj = new Star();
            starobj.starId = allwaypointsWithConnection2.getString("StarId");
            starobj.setLat(allwaypointsWithConnection2.getDouble("Lat"));
            starobj.setLon(allwaypointsWithConnection2.getDouble("Lon"));
            this.starList.add(starobj);
        }
    }

    public void getApproaches () throws SQLException {
        String queryApp = "with cte_Approach\n" +
                "as(\n" +
                "select app.ApproachId from dbo.Approach as app\n" +
                "inner join db_Airport as air\n" +
                "on app.iCAOCode = air.ICAOCode \n" +
                "where app.iCAOCode = '"+ this.airportName +"'\n" +
                "group by ApproachId),\n" +
                "\n" +
                "cte_Airway\n" +
                "as(select aw.FixId from db_Airway as aw\n" +
                "),\n" +
                "\n" +
                "cte_all \n" +
                "as\n" +
                "(\n" +
                "select aw.FixID, ap.ApproachId from cte_Airway as aw\n" +
                "left join cte_Approach as ap on ap.ApproachId = aw.FixID\n" +
                " \n" +
                ")\n" +
                "\n" +
                "select distinct FixID from cte_all\n" +
                "where cte_all.ApproachId is not null\n" +
                ";";

        ResultSet allwaypointsWithConnectionApp = SQLConnector.fetchAll(queryApp);

        ArrayList <String> arrayString = new ArrayList<>();
        while (allwaypointsWithConnectionApp.next()){
            arrayString.add(allwaypointsWithConnectionApp.getString("FixID"));
        }
        for(String s : arrayString){
            String queryApp2 = "select app.ApproachId, app.Lat, app.Lon from dbo.Approach as app\n" +
                    "inner join db_Airport as air\n" +
                    "on app.iCAOCode = air.ICAOCode\n" +
                    "where app.iCAOCode = '"+this.airportName+"' and ApproachId = '"+s+"'\n" +
                    ";";
            ResultSet allwaypointsWithConnectionApp2 = SQLConnector.fetchAll(queryApp2);
            allwaypointsWithConnectionApp2.next();
            Approach appObj = new Approach();
            appObj.approachId = allwaypointsWithConnectionApp2.getString("ApproachId");
            appObj.setLat(allwaypointsWithConnectionApp2.getDouble("Lat"));
            appObj.setLon(allwaypointsWithConnectionApp2.getDouble("Lon"));
            this.approachList.add(appObj);
        }
    }

    public void getFinals () throws SQLException {
        String queryFinal = "with cte_Final\n" +
                "as(\n" +
                "select fin.FinalId from dbo.Final as fin\n" +
                "inner join db_Airport as air\n" +
                "on fin.iCAOCode = air.ICAOCode \n" +
                "where fin.iCAOCode = '"+this.airportName+"'\n" +
                "group by FinalId),\n" +
                "\n" +
                "cte_Airway\n" +
                "as(select aw.FixId from db_Airway as aw\n" +
                "),\n" +
                "\n" +
                "cte_all \n" +
                "as\n" +
                "(\n" +
                "select aw.FixID, fi.FinalId from cte_Airway as aw\n" +
                "left join cte_Final as fi on fi.FinalId = aw.FixID\n" +
                " \n" +
                ")\n" +
                "\n" +
                "select distinct FixID from cte_all\n" +
                "where cte_all.FinalId is not null\n" +
                ";";

        ResultSet allwaypointsWithConnectionFinal = SQLConnector.fetchAll(queryFinal);

        ArrayList <String> arrayString = new ArrayList<>();
        while (allwaypointsWithConnectionFinal.next()){
            arrayString.add(allwaypointsWithConnectionFinal.getString("FixID"));
        }
        for(String s : arrayString){
            String queryFinal2 = "select fin.FinalId, fin.Lat, fin.Lon from dbo.Final as fin\n" +
                    "inner join db_Airport as air\n" +
                    "on fin.iCAOCode = air.ICAOCode\n" +
                    "where fin.iCAOCode = '"+this.airportName+"' and FinalId = '"+s+"'\n" +
                    ";";
            ResultSet allwaypointsWithConnectionFinal2 = SQLConnector.fetchAll(queryFinal2);
            allwaypointsWithConnectionFinal2.next();
            Final finObj = new Final();
            finObj.finalId = allwaypointsWithConnectionFinal2.getString("FinalId");
            finObj.setLat(allwaypointsWithConnectionFinal2.getDouble("Lat"));
            finObj.setLon(allwaypointsWithConnectionFinal2.getDouble("Lon"));
            this.finalList.add(finObj);
        }
    }

    public static ArrayList<String> getAirportsWithoutSids () throws SQLException {
        String query = "SELECT ap.ICAOCode as apIC\n" +
                "FROM dbo.db_Airport ap\n" +
                "LEFT JOIN dbo.Sid sidi ON sidi.iCAOCode = ap.ICAOCode\n" +
                "WHERE sidi.iCAOCode IS NULL\n" +
                "order by apIC;";

        ResultSet allwaypointsWithConnection3 = SQLConnector.fetchAll(query);

        ArrayList <String> airportWithoutSids = new ArrayList<>();
        while (allwaypointsWithConnection3.next()){
            airportWithoutSids.add(allwaypointsWithConnection3.getString("apIC"));
        }
        return airportWithoutSids;
    }

    public static ArrayList<String> getAirportsWithoutStars () throws SQLException {
        String query = "SELECT ap.ICAOCode as apIC\n" +
                "FROM dbo.db_Airport ap\n" +
                "LEFT JOIN dbo.Star star ON star.iCAOCode = ap.ICAOCode\n" +
                "WHERE star.iCAOCode IS NULL\n" +
                "order by apIC;";

        ResultSet allwaypointsWithConnection4 = SQLConnector.fetchAll(query);

        ArrayList <String> airportWithoutStars = new ArrayList<>();
        while (allwaypointsWithConnection4.next()){
            airportWithoutStars.add(allwaypointsWithConnection4.getString("apIC"));
        }
        return airportWithoutStars;
    }

    public static ArrayList<String> getAirportsWithoutStarsAndApproaches () throws SQLException {
        String query = "SELECT ap.ICAOCode as apIC, approach.iCAOCode, star.iCAOCode\n" +
                "FROM dbo.db_Airport ap\n" +
                "LEFT JOIN dbo.Approach approach ON approach.iCAOCode = ap.ICAOCode\n" +
                "left join dbo.Star as star on star.iCAOCode = ap.ICAOCode\n" +
                "WHERE approach.iCAOCode IS NULL and star.iCAOCode is null\n" +
                "order by apIC;";

        ResultSet allwaypointsWithConnection4 = SQLConnector.fetchAll(query);

        ArrayList <String> airportWithoutStarsAndApproaches = new ArrayList<>();
        while (allwaypointsWithConnection4.next()){
            airportWithoutStarsAndApproaches.add(allwaypointsWithConnection4.getString("apIC"));
        }
        return airportWithoutStarsAndApproaches;
    }

    public static ArrayList<String> getAirportsWithoutStarsAndApproachesAndFinals () throws SQLException {
        String query = "With cteArSt (arIcao,starIcao)\n" +
                "as ( \n" +
                "select ar.ICAOCode, st.distance\n" +
                "from dbo.db_Airport as ar\n" +
                "\tleft join dbo.Star as st on ar.ICAOCode = st.iCAOCode\n" +
                "\twhere st.iCAOCode is not null),\n" +
                "\n" +
                "\tcteArAp (arIcao, apIcao)\n" +
                "\tas (\n" +
                "\tselect ar.ICAOCode, ap.iCAOCode from dbo.db_Airport as ar\n" +
                "\tleft join dbo.Approach as ap on ar.ICAOCode = ap.iCAOCode\n" +
                "\twhere ap.iCAOCode is not null\n" +
                "\t),\n" +
                "\n" +
                "\tcteArFi (arIcao, fiIcao)\n" +
                "\tas (\n" +
                "\tselect ar.ICAOCode, fi.iCAOCode from dbo.db_Airport as ar\n" +
                "\tleft join dbo.Final as fi on ar.ICAOCode = fi.iCAOCode\n" +
                "\twhere fi.iCAOCode is not null\n" +
                "\t),\n" +
                "\n" +
                "\tcteAll (arIcao, stIcao, apIcao, fiIcao)\n" +
                "\tas\n" +
                "\t(\n" +
                "\tselect ar.ICAOCode, cteArSt.arIcao, cteArAp.arIcao, cteArFi.arIcao\n" +
                "\tfrom dbo.db_Airport as ar\n" +
                "\tleft join cteArSt on cteArSt.arIcao = ar.ICAOCode\n" +
                "\tleft join cteArAp on cteArAp.arIcao = ar.ICAOCode\n" +
                "\tleft join cteArFi on cteArFi.arIcao = ar.ICAOCode\n" +
                "\twhere cteArSt.arIcao is null and cteArAp.arIcao is null and cteArFi.arIcao is null\n" +
                "\t)\n" +
                "\tselect arIcao from cteAll";

        ResultSet allwaypointsWithConnection4 = SQLConnector.fetchAll(query);

        ArrayList <String> airportWithoutStarsAndApproachesAndFinals = new ArrayList<>();
        while (allwaypointsWithConnection4.next()){
            airportWithoutStarsAndApproachesAndFinals.add(allwaypointsWithConnection4.getString("arIcao"));
        }
        return airportWithoutStarsAndApproachesAndFinals;
    }

    public void getNextFixSid() throws SQLException {
        latPlus += 0.05;
        latMinus -= 0.05;
        lonPlus += 0.05;
        lonMinus -= 0.05;

        String query = "select * from dbo.db_Airway as fix\n" +
                "where fix.Lat < "+latPlus+"\n" +
                "and fix.Lat >  "+latMinus+"\n" +
                "and fix.Lon < "+lonPlus+"\n" +
                "and fix.Lon > "+lonMinus+";";

        ResultSet allWaypointsnextToAirport = SQLConnector.fetchAll(query);

        generate:
        while (allWaypointsnextToAirport.next()){

            if(this.sidList.size() < 3){
                Sid sidObj = new Sid();
                sidObj.sidId = allWaypointsnextToAirport.getString("FixID");
                sidObj.setLat(allWaypointsnextToAirport.getDouble("Lat"));
                sidObj.setLon(allWaypointsnextToAirport.getDouble("Lon"));
                if(!sidListStrings.contains(sidObj.sidId)){
                    this.sidList.add(sidObj);
                    this.sidListStrings.add(sidObj.sidId);
                }

            } else {
                break generate;
            }
        }
        while (sidList.size() < 3){
            getNextFixSid();
        }
    }

    public void getNextFixStar() throws SQLException {
        latPlus += 0.05;
        latMinus -= 0.05;
        lonPlus += 0.05;
        lonMinus -= 0.05;

        String query = "select * from dbo.db_Airway as fix\n" +
                "where fix.Lat < "+latPlus+"\n" +
                "and fix.Lat >  "+latMinus+"\n" +
                "and fix.Lon < "+lonPlus+"\n" +
                "and fix.Lon > "+lonMinus+";";

        ResultSet allWaypointsnextToAirport = SQLConnector.fetchAll(query);

        generate:
        while (allWaypointsnextToAirport.next()) {

            if (this.starList.size() < 3) {
                Star starObj = new Star();
                starObj.starId = allWaypointsnextToAirport.getString("FixID");
                starObj.setLat(allWaypointsnextToAirport.getDouble("Lat"));
                starObj.setLon(allWaypointsnextToAirport.getDouble("Lon"));
                if(!starListStrings.contains(starObj.starId)){
                    this.starList.add(starObj);
                    this.starListStrings.add(starObj.starId);
                }

            } else {
                break generate;
            }
        }
        while (starList.size() < 3){
            getNextFixStar();
        }
    }

    public void fillLatLon (){
        this.latPlus = this.getLat();
        this.latMinus = this.getLat();
        this.lonPlus = this.getLon();
        this.lonMinus = this.getLon();
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public String getAirportName() {
        return airportName;
    }

}
