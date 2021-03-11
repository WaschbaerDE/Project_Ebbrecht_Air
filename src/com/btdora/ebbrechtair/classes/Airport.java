package com.btdora.ebbrechtair.classes;

public class Airport extends GeoCoordinate {
    private final String icaoCode;
    private String airportName;
    private int altitudeAirportInFeet;
    private int maxRunwayLength;
    private int ifr;

    //full constructor for the creation of an Airport
    public Airport(String icaoCode, String airportName, double lat, double lon, int altitudeAirportInFeet, int maxRunwayLength, int ifr) {
        super(lat, lon);
        this.icaoCode = icaoCode;
        this.airportName = airportName;
        this.altitudeAirportInFeet = altitudeAirportInFeet;
        this.maxRunwayLength = maxRunwayLength;
        this.ifr = ifr;
    }
    
    //test-data constructor
    public Airport(String icaoCode, Double lat, Double lon) {
        super(lat, lon);
        this.icaoCode = icaoCode;
    }

    public String toString() {
        return this.icaoCode+" "+this.airportName;
    }
}
