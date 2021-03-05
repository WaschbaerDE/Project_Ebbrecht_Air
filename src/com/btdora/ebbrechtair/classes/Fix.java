package com.btdora.ebbrechtair.classes;

public class Fix {
    private String FixID;

    public Fix(String fixID, String areacode, String departure, String arrival) {
        FixID = fixID;
        Areacode = areacode;
        Departure = departure;
        Arrival = arrival;
    }

    private String Areacode;
    private String Departure;
    private String Arrival;
}
