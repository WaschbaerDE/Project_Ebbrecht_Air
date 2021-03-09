package com.btdora.ebbrechtAir.classes;

import com.btdora.ebbrechtAir.Airport;

import java.util.ArrayList;

public class Fix extends GeoCoordinate {
    private String FixID;
    private String Areacode;
    private String Departure;
    private String Arrival;

    public Fix(String fixID, double Lat, double Lon, String areacode, String departure, String arrival) {
        super(Lat, Lon);
        FixID = fixID;
        Areacode = areacode;
        Departure = departure;
        Arrival = arrival;


        ArrayList<Fix> fixArrayList = new ArrayList<Fix>();


    }


} 
//    public Fix() {
//        
//        
//
//        ArrayList<Fix> fixArrayList = new ArrayList<Fix>();
//        com.btdora.ebbrechtAir.classes.Fix fix1 = new com.btdora.ebbrechtAir.classes.Fix("Affe", 23.426944, -47.165833, "affenaa", "Affent", "eeeee");
//        fixArrayList.add(fix1);
//
//        com.btdora.ebbrechtAir.classes.Fix fix2 = new com.btdora.ebbrechtAir.classes.Fix("Affe", -22.908333, -43.196388, "affenaa", "Affent", "eeeee");
//        fixArrayList.add(fix2);
//
//        ArrayList<GeoCoordinate> objects = new ArrayList<GeoCoordinate>();
//        objects.add(3, fix1);
//        objects.add(7, fix2);
//        }
//    }


    

