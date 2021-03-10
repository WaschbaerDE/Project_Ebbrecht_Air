package com.btdora.ebbrechtair.classes;

import java.util.ArrayList;

public class AirwayPart extends GeoCoordinate{
    private String AirwayID;
    private int AtsPartition;
    private String FixID;
    private String IDOfNextFix;
    private Double LonNext;
    private Double LatNext;
    private int InBoundCourse;
    private int OutBoundCourse;
    private Double LegLength;
    private ArrayList<AirwayPart> airway;



    public AirwayPart(String airwayID, int atsPartition, String fixID, double Lat, double Lon, String IDOfNextFix, Double lonNext, Double latNext, int inBoundCourse, int outBoundCourse, Double legLength,ArrayList<AirwayPart> airway) {
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
        this.airway = airway;
    }


}
