package com.btdora.ebbrechtAir;

public class Airport {
    String name;
    double latitude;
    double longitude;

    public Airport(String name, double lat, double lon){
        this.name = name;
        this.latitude = (lat*5)+500;
        this.longitude = (lon*5)+500;
    }
}
