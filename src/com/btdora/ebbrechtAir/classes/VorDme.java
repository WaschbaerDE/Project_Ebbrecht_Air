package com.btdora.ebbrechtAir.classes;

public class VorDme extends Navaid {
    public VorDme(String navaidID, String navaidName, Double frequency, double Lat, double Lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, 1, 1, Lat, Lon, altitude, areaCode);
    }
}
