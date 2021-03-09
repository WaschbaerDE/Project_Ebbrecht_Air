package com.btdora.ebbrechtAir.classes;

public class Navaid extends GeoCoordinate {
    private String NavaidID;
    private String NavaidName;
    private Double Frequency;
    private int RadialCapability;
    private int DMECapability;
    private  String a01; //Unbekannte Varibale Platzhalter für den Fall einer Verwendung
    private int Altitude;
    private String AreaCode;
    private String a02; //Unbekannte Varibale Platzhalter für den Fall einer Verwendung

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
