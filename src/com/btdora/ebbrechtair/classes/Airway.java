package com.btdora.ebbrechtair.classes;

public class Airway extends GeoCoordinate{
    private String AtsID;
    private int AtsPartition;
    private String FixID;
    private String IDOfNextFix;
    private Double LonNext;
    private Double LatNext;
    private int InBoundCourse;
    private int OutBoundCourse;
    private Double LegLength;

    public Airway( String atsID, int atsPartition, String fixID, Double lat, Double lon, String IDOfNextFix, Double lonNext, Double latNext, int inBoundCourse, int outBoundCourse, Double legLength) {
        super(lat, lon);
        AtsID = atsID;
        AtsPartition = atsPartition;
        FixID = fixID;
        this.IDOfNextFix = IDOfNextFix;
        LonNext = lonNext;
        LatNext = latNext;
        InBoundCourse = inBoundCourse;
        OutBoundCourse = outBoundCourse;
        LegLength = legLength;
    }


    public String getAtsID() {
        return AtsID;
    }

    public Double getLonNext() {
        return LonNext;
    }

    public void setLonNext(Double lonNext) {
        LonNext = lonNext;
    }

    public Double getLatNext() {
        return LatNext;
    }

    public void setLatNext(Double latNext) {
        LatNext = latNext;
    }
}
