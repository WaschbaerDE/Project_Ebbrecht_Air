package com.btdora.ebbrechtAir.classes;

public class Ndb extends Navaid {
    public Ndb(String navaidID, String navaidName, Double frequency, double lat ,double lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, 0, 0,lat,lon,altitude,null);
    }
}
