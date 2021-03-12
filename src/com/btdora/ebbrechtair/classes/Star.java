package com.btdora.ebbrechtair.classes;

public class Star extends GeoCoordinate {
    String starId;
    String starUniqueId;

    public Star() {
    }

    public Star(String starId, String starUniqueId) {
        this.starId = starId;
        this.starUniqueId = starUniqueId;
    }

    public String getStarId() {
        return starId;
    }

    public void setStarId(String starId) {
        this.starId = starId;
    }

    public String getStarUniqueId() {
        return starUniqueId;
    }

    public void setStarUniqueId(String starUniqueId) {
        this.starUniqueId = starUniqueId;
    }
}
