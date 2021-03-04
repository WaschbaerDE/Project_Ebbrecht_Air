package com.btdora.ebbrechtAir;

import java.util.ArrayList;

public class Testdaten {
    static ArrayList<Airport> airportsArray = new ArrayList<Airport>();

    public void airportsTest () {
        Airport fra = new Airport("Frankfurt", 50.033306, -8.570456);
        airportsArray.add(fra);

        Airport ber = new Airport("Berlin", 44.575361, 71.175917);
        airportsArray.add(ber);
    }

}
