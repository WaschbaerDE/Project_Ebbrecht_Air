package com.btdora.ebbrechtair.classes;

public class Dme extends Navaid {
    public Dme(String navaidID, String navaidName, Double frequency, double lat, double lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, 0, 1, lat, lon, altitude, areaCode);
    }
}