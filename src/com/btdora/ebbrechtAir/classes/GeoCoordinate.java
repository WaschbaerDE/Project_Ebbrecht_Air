package com.btdora.ebbrechtAir.classes;

public abstract class GeoCoordinate {
    private Double Lat;
    private Double Lon;

    public GeoCoordinate(Double lat, Double lon) {
        Lat = lat;
        Lon = lon;
    }

    public Double getLat() {return Lat;}

    public Double getLon() {
        return Lon;
    }
}
