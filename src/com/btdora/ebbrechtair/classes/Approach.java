package com.btdora.ebbrechtair.classes;

public class Approach extends GeoCoordinate {
    String approachId;
    String approachUniqueId;

    public Approach() {
    }

    public String getApproachId() {
        return approachId;
    }

    public void setApproachId(String approachId) {
        this.approachId = approachId;
    }

    public String getApproachUniqueId() {
        return approachUniqueId;
    }

    public void setApproachUniqueId(String approachUniqueId) {
        this.approachUniqueId = approachUniqueId;
    }
}
