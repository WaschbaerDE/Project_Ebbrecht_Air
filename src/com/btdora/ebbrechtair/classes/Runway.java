package com.btdora.ebbrechtair.classes;

public class Runway extends GeoCoordinate {
    private final String Airportcode;
    private final String RunwayIdentifier;
    private final int Heading;
    private final int MaxTakeoffLength;
    private final int RunwayWidthFeet;
    private final String LOC_ILS;
    private final Double ILSFrequency1;
    private final Double ILSFrequency2;
    private final int TouchdownAltitude;
    private final Double ApproachGlideslope;
    private final String a01; //Unbekannte Varibale Platzhalter für den Fall einer Verwendung
    private final String a02; //Unbekannte Varibale Platzhalter für den Fall einer Verwendung
    private final String a03; //Unbekannte Varibale Platzhalter für den Fall einer Verwendung

    public Runway(String airportcode, String runwayIdentifier, int heading, int maxTakeoffLength, int runwayWidthFeet, String LOC_ILS, Double ILSFrequency1, Double ILSFrequency2, double Lat, double Lon, int touchdownAltitude, Double approachGlideslope, String a01, String a02, String a03) {
        super(Lat,Lon);
        Airportcode = airportcode;
        RunwayIdentifier = runwayIdentifier;
        Heading = heading;
        MaxTakeoffLength = maxTakeoffLength;
        RunwayWidthFeet = runwayWidthFeet;
        this.LOC_ILS = LOC_ILS;
        this.ILSFrequency1 = ILSFrequency1;
        this.ILSFrequency2 = ILSFrequency2;
        TouchdownAltitude = touchdownAltitude;
        ApproachGlideslope = approachGlideslope;
        this.a01 = a01;
        this.a02 = a02;
        this.a03 = a03;
    }

}
