package com.btdora.ebbrechtAir.classes;

public abstract class Navaid extends GeoCoordinate {
    private String navaidID;
    private String navaidName;
    private Double frequency;
    private boolean radialCapability;
    private boolean dmeCapability;
    private int altitude;
    private String areaCode;
    

    public Navaid(String navaidID, String navaidName, Double frequency, int radialCapability, int dmeCapability, double lat, double lan, int altitude, String areaCode) {
        super(lat, lan);
        this.navaidID = navaidID;
        this.navaidName = navaidName;
        this.frequency = frequency;
        this.altitude = altitude;
        this.areaCode = areaCode;
        if (radialCapability== 1) {
            this.radialCapability =true;
        }
        else {
            this.radialCapability =false;
        }

        if (dmeCapability== 1) {
            this.dmeCapability =true;
        }
        else {
            this.dmeCapability =false;
        }


    }

    public String getNavaidID() {
        return this.navaidID;
    }

    public String getNavaidName() {
        return this.navaidName;
    }

    public Double getFrequency() {
        return this.frequency;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public boolean hasRadialCapability() {
        return this.radialCapability;
    }

    public boolean hasDMECapability() {
        return this.dmeCapability;
    }
}
