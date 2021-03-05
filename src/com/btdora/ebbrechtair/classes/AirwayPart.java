package com.btdora.ebbrechtair.classes;

public class AirwayPart {
    private String AtsID;
    private int AtsPartition;
    private String FixID;
    private String IDOfNextFix;
    private Double LonNext;

    public AirwayPart(String atsID, int atsPartition, String fixID, String IDOfNextFix, Double lonNext, Double latNext, int inBoundCourse, int outBoundCourse, Double legLength) {
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

    private Double LatNext;
    private int InBoundCourse;
    private int OutBoundCourse;
    private Double LegLength;
}
