package com.btdora.ebbrechtair.classes;

public class Fix extends AirObject {
    private String FixID;
    private String Areacode;
    private String Departure;
    private String Arrival;

    public Fix(String fixID, String areacode, String departure, String arrival) {
        FixID = fixID;
        Areacode = areacode;
        Departure = departure;
        Arrival = arrival;
    }


}
