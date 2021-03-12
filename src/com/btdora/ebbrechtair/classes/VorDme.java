package com.btdora.ebbrechtair.classes;

//Klasse definiert Navaid als VOR/DME
//VOR/DME = Drehfunkfeuer/Funkfeuer für die Luftfahrtnavigation


public class VorDme extends Navaid {
    public VorDme(String navaidID, String navaidName, Double frequency, double Lat, double Lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, 1, 1, Lat, Lon, altitude, areaCode);
    }
}
