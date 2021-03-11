package com.btdora.ebbrechtAir.classes;

public class Airport extends GeoCoordinate {
    private String ICAOCode;
    private int ifr;
    private String airportname;
    private int altitudeairportInFeet;
    private int maxRunwayLength;




    public Airport(String ICAOCode, int ifr, String airportname, double lat,double lon, int altitudeairportInFeet, int maxRunwayLength) {
        super(lat,lon);
        this.ICAOCode = ICAOCode;
        this.ifr = ifr;
        airportname = airportname;
        altitudeairportInFeet = altitudeairportInFeet;
        maxRunwayLength = maxRunwayLength;


    }

    public int getifr() {
        return ifr;
    }

    public void setifr(int ifr) {
        this.ifr = ifr;
    }

}
