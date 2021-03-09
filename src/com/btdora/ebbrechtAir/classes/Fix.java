package com.btdora.ebbrechtAir.classes;

public class Fix extends GeoCoordinate {
    private String FixID;
    private String Areacode;
    private String Departure;
    private String Arrival;

    public Fix(String fixID, double Lat, double Lon, String areacode, String departure, String arrival) {
        super(Lat,Lon);
        FixID = fixID;
        Areacode = areacode;
        Departure = departure;
        Arrival = arrival;
    }
}
