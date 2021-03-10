package com.btdora.ebbrechtair.classes;

public class Airport extends GeoCoordinate {
    private String ICAOCode;
    private String AirportName;
    private int AltitudeAirportInFeet;
    private String a01;//Unbekannte Varibale Platzhalter für den Fall einer Verwendung
    private String a02;//Unbekannte Varibale Platzhalter für den Fall einer Verwendung
    private int MaxRunwayLength;
    private String b01;//Unbekannte Varibale Platzhalter für den Fall einer Verwendung
    private int IFR;

    public Airport(String ICAOCode, String airportName, double Lat,double Lon, int altitudeAirportInFeet, String a01, String a02, int maxRunwayLength, String b01, int IFR) {
        super(Lat,Lon);
        this.ICAOCode = ICAOCode;
        AirportName = airportName;
        AltitudeAirportInFeet = altitudeAirportInFeet;
        this.a01 = a01;
        this.a02 = a02;
        MaxRunwayLength = maxRunwayLength;
        this.b01 = b01;
        this.IFR = IFR;
    }

    public String toString() {
        return this.ICAOCode+" "+this.AirportName;
    }
}