package com.btdora.ebbrechtair.classes;

public abstract class GeoCoordinate {
    private final Double Lat;
    private final Double Lon;

    public GeoCoordinate(Double lat, Double lon) {
        Lat = lat;
        Lon = lon;
    }

    public Double getLat() {
        return Lat;
    }

    public Double getLon() {
        return Lon;
    }
}
