package com.btdora.ebbrechtair.classes;

//Klasse schaut sich die Navaids an und gibt an ob es sich um ein VOR handelt.
//Klasse definiert Vor=VHF. VHF Omnidirectional Radio Range = Vor.

public class Vor extends Navaid {
    public Vor(String navaidID, String navaidName, Double frequency, double Lat, double Lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, 1, 0, Lat, Lon, altitude, areaCode);
    }
}
