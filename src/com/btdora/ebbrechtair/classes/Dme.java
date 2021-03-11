package com.btdora.ebbrechtair.classes;


// Überprüft ob es sich bei der Navaid um ein DME handelt
// DME  ist eine Funkstelle des Navigationsfunkdienstes, deren Aussendungen dazu bestimmt sind, einem
// Luftfahrzeug die Feststellung seiner Entfernung in Bezug auf einen ortsfesten Referenzpunkt zu ermöglichen.

public class Dme extends Navaid {
    public Dme(String navaidID, String navaidName, Double frequency, double lat, double lon, int altitude, String areaCode) {
        super(navaidID, navaidName, frequency, 0, 1, lat, lon, altitude, areaCode);
    }
}