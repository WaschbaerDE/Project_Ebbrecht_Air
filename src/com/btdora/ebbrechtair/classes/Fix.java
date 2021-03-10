package com.btdora.ebbrechtair.classes;

public class Fix extends GeoCoordinate {
    private final String FixID;
    private final String Areacode;
    private final String Departure;
    private final String Arrival;

    public Fix(String fixID, double Lat, double Lon, String areacode, String departure, String arrival) {
        super(Lat,Lon);
        FixID = fixID;
        Areacode = areacode;
        Departure = departure;
        Arrival = arrival;

    }


}
