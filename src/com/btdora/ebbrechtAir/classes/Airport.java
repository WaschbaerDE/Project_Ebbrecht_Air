package com.btdora.ebbrechtAir.classes;

public class Airport extends GeoCoordinate {
    private String ICAOCode;
    private String AirportName;
    private int AltitudeAirportInFeet;
    private int MaxRunwayLength;


    public Airport(String ICAOCode, String airportName, double lat,double lon, int altitudeAirportInFeet, int maxRunwayLength) {
        super(lat,lon);
        this.ICAOCode = ICAOCode;
        AirportName = airportName;
        AltitudeAirportInFeet = altitudeAirportInFeet;
        MaxRunwayLength = maxRunwayLength;

    }


}
