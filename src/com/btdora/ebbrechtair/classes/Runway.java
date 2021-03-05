package com.btdora.ebbrechtair.classes;

public class Runway extends AirObject {
    private String Airportcode;
    private String RunwayIdentifier;
    private int Heading;
    private int MaxTakeoffLength;
    private int RunwayWidthFeet;
    private String LOC_ILS;
    private Double ILSFrequency1;
    private Double ILSFrequency2;
    private int TouchdownAltitude;
    private Double ApproachGlideslope;
    private String a01;
    private String a02;
    private String a03;

    public Runway(String airportcode, String runwayIdentifier, int heading, int maxTakeoffLength, int runwayWidthFeet, String LOC_ILS, Double ILSFrequency1, Double ILSFrequency2, int touchdownAltitude, Double approachGlideslope, String a01, String a02, String a03) {
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
