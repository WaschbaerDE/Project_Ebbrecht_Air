package com.btdora.ebbrechtair;

import com.btdora.ebbrechtair.classes.Airport;
import com.btdora.ebbrechtair.classes.Fix;
import com.btdora.ebbrechtair.classes.GeoCoordinate;
import com.btdora.ebbrechtair.classes.VorDme;

import java.util.ArrayList;

public class TestRoute {
    private ArrayList<GeoCoordinate> testroute =  new ArrayList<GeoCoordinate>();

    public ArrayList<GeoCoordinate> getList() {
        testroute.add(new Airport("EDFE", "FRANKFURT-EGELSBACH", 49.960822, 8.641461, 385, 4500, 0));
        testroute.add(new Fix("NOKDI",	49.866389,	8.479167,	"ED","0","0"));
        testroute.add(new VorDme("RID",	"RIED",	112.200000,		49.781692,	8.541486,	337,	"ED"));
        testroute.add(new Fix("ODAVU",	49.725556,	8.533889,	"ED","0","0"));
        testroute.add(new Fix("ANEKI",	49.317272,	8.480428,	"ED","0","0"));
        testroute.add(new Fix("NEKLO",	49.286111,	8.476389,	"ED","0","0"));
        testroute.add(new Fix("MANEM",	49.294583,	8.191658,	"ED","0","0"));
        testroute.add(new Fix("LADAT",	49.265256,	7.839472,	"ED","0","0"));
        testroute.add(new VorDme("ZWN",	"ZWEIBRUECKEN",	114.800000,		49.229072,	7.417892,	1164,	"ED"));
        testroute.add(new Airport("EDRZ", "ZWEIBRUECKEN", 49.209525, 7.400647, 1133, 8700, 1));
        return this.testroute;
    }
}
