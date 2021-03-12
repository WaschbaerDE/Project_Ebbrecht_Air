package com.btdora.ebbrechtair.classes;

public class Sid extends GeoCoordinate {
    String sidId;
    String sidUniqueId;


    public Sid(Double lat, Double lon, String sidId, String sidUniqueId) {
        super(lat, lon);
        this.sidId = sidId;
        this.sidUniqueId = sidUniqueId;
    }

    public Sid() {
    }

    public void setSidId(String sidId) {
        this.sidId = sidId;
    }

    public void setSidUniqueId(String sidUniqueId) {
        this.sidUniqueId = sidUniqueId;
    }

    public String getSidId() {
        return sidId;
    }

    public String getSidUniqueId() {
        return sidUniqueId;
    }

    public void fillSid (String sidName){

    }


}
