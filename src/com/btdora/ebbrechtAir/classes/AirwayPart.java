package com.btdora.ebbrechtAir.classes;

public class AirwayPart {
    private String AirwayID;
    private int AtsPartition;
    private String FixID;
    private String IDOfNextFix;
    private Double LonNext;
    private Double LatNext;
    private int InBoundCourse;
    private int OutBoundCourse;
    private Double LegLength;




    public AirwayPart(String airwayID, int atsPartition, String fixID, String IDOfNextFix, Double lonNext, Double latNext, int inBoundCourse, int outBoundCourse, Double legLength) {
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
