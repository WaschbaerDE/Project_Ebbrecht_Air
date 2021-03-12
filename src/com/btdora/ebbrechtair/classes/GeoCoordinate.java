package com.btdora.ebbrechtair.classes;

public abstract class GeoCoordinate {
    private Double Lat;
    private Double Lon;

    public GeoCoordinate(Double lat, Double lon) {
        Lat = lat;
        Lon = lon;
    }

    public GeoCoordinate() {
        
    }


    public Double getLat() {
        return Lat;
    }

    public Double getLon() {
        return Lon;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public void setLon(Double lon) {
        Lon = lon;
    }
}
