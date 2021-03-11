package com.btdora.ebbrechtAir.classes;

public class Vor extends Navaid {
    public Vor(String navaidID, String navaidName, Double frequency, double Lat, double Lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, 1, 0, Lat, Lon, altitude, areaCode);
    }
}
