package com.btdora.ebbrechtair.classes;

public class Navaid extends GeoCoordinate {
    private final String NavaidID;
    private final String NavaidName;
    private final Double Frequency;
    private final int RadialCapability;
    private final int DMECapability;
    private final String a01; //Unbekannte Varibale Platzhalter für den Fall einer Verwendung
    private final int Altitude;
    private final String AreaCode;
    private final String a02; //Unbekannte Varibale Platzhalter für den Fall einer Verwendung

    public Navaid(String navaidID, String navaidName, Double frequency, int radialCapability, int DMECapability, String a01, double Lat, double Lon, int altitude, String areaCode, String a02) {
        super(Lat,Lon);
        NavaidID = navaidID;
        NavaidName = navaidName;
        Frequency = frequency;
        RadialCapability = radialCapability;
        this.DMECapability = DMECapability;
        this.a01 = a01;
        Altitude = altitude;
        AreaCode = areaCode;
        this.a02 = a02;
    }

}
