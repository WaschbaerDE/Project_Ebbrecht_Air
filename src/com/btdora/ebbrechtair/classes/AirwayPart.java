package com.btdora.ebbrechtair.classes;

public class AirwayPart extends GeoCoordinate{
    private final String AirwayID;
    private final int AtsPartition;
    private final String FixID;
    private final String IDOfNextFix;
    private final Double LonNext;
    private final Double LatNext;
    private final int InBoundCourse;
    private final int OutBoundCourse;
    private final Double LegLength;

    public AirwayPart(String airwayID, int atsPartition, String fixID, double Lat, double Lon, String IDOfNextFix, Double lonNext, Double latNext, int inBoundCourse, int outBoundCourse, Double legLength) {
        super(Lat,Lon);
        AirwayID = airwayID;
        AtsPartition = atsPartition;
        FixID = fixID;
        this.IDOfNextFix = IDOfNextFix;
        LonNext = lonNext;
        LatNext = latNext;
        InBoundCourse = inBoundCourse;
        OutBoundCourse = outBoundCourse;
        LegLength = legLength;
    }

}
