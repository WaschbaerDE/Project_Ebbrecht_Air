package com.btdora.ebbrechtair.classes;


import java.awt.*;
import java.util.ArrayList;

public class Airway extends AirObject {
    private ArrayList<AirwayPart> airway;

    public Airway(ArrayList<AirwayPart> airway) {
        this.airway = airway;
    }
}
