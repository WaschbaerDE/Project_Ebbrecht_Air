package com.btdora.ebbrechtair.classes;

public class Airport extends AirObject {
    private String ICAOCode;
    private String AirportName;
    private int AltitudeAirportInFeet;
    private String a01;
    private String a02;
    private int MaxRunwayLength;
    private String b01;

    public Airport(String ICAOCode, String airportName, int altitudeAirportInFeet, String a01, String a02, int maxRunwayLength, String b01) {
        this.ICAOCode = ICAOCode;
        AirportName = airportName;
        AltitudeAirportInFeet = altitudeAirportInFeet;
        this.a01 = a01;
        this.a02 = a02;
        MaxRunwayLength = maxRunwayLength;
        this.b01 = b01;
    }


}
