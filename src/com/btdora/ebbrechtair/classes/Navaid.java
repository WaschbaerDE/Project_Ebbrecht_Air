package com.btdora.ebbrechtair.classes;

public class Navaid {
    private String NavaidID;
    private String NavaidName;

    public Navaid(String navaidID, String navaidName, Double frequency, int radialCapability, int DMECapability, String a01, int altitude, String areaCode, String a02) {
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

    private Double Frequency;
    private int RadialCapability;
    private int DMECapability;
    private  String a01;
    private int Altitude;
    private String AreaCode;
    private String a02;
}
