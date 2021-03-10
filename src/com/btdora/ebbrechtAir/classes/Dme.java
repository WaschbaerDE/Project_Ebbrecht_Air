package com.btdora.ebbrechtAir.classes;

public class Dme extends Navaid {
    public Dme(String navaidID, String navaidName, Double frequency, double lat, double lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, false, true, lat, lon, altitude, areaCode);
    }
}