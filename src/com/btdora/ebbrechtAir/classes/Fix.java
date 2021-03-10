package com.btdora.ebbrechtAir.classes;

import java.util.ArrayList;

public class Fix extends GeoCoordinate {
    private String fixID;
    private String areacode;
    private String departure;
    private String arrival;

    public Fix(String fixID, double lat, double lon, String areacode, String departure, String arrival) {
        super(lat, lon);
        this.fixID = fixID;
        this.areacode = areacode;
        this.departure = departure;
        this.arrival = arrival;


        ArrayList<Fix> fixArrayList = new ArrayList<Fix>();


    }


} 



    

