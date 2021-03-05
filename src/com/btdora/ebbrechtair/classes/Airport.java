package com.btdora.ebbrechtair.classes;

public class Airport extends GeoCoordinate {
    private String ICAOCode;
    private String AirportName;
    private int AltitudeAirportInFeet;
    private int MaxRunwayLength;

    public Airport(String ICAOCode, String airportName, int altitudeAirportInFeet, double Lat, double Lon, int maxRunwayLength) {
        super(Lat,Lon);
        this.ICAOCode = ICAOCode;
        AirportName = airportName;
        AltitudeAirportInFeet = altitudeAirportInFeet;
        MaxRunwayLength = maxRunwayLength;
    }

    public String toString() {
        return this.ICAOCode+" "+this.AirportName;
    }


}
