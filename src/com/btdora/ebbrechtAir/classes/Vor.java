package com.btdora.ebbrechtAir.classes;

public class Vor extends Navaid {
    public Vor(String navaidID, String navaidName, Double frequency, int radialCapability, int DMECapability, double Lat, double Lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, true, false, Lat, Lon, altitude, areaCode);
    }
}
