package com.btdora.ebbrechtAir;

import com.btdora.ebbrechtAir.classes.GeoCoordinate;

public class Airport extends GeoCoordinate {
    String name;
    double latitude;
    double longitude;

    public Airport(String name, double lat, double lon){
        super();
        this.name = name;
        this.latitude = lat;
        this.longitude = lon;
    }
}
