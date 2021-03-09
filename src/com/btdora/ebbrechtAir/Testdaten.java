package com.btdora.ebbrechtAir;

import com.btdora.ebbrechtAir.classes.Fix;
import com.btdora.ebbrechtAir.classes.GeoCoordinate;

import java.util.ArrayList;

public class Testdaten {
    ArrayList<GeoCoordinate> objects = new ArrayList<GeoCoordinate>();
    static ArrayList<Airport> airportsArray = new ArrayList<Airport>();
    static ArrayList<Fix> fixArrayList = new ArrayList<Fix>();


    public void airportsTest () {
        Airport fra = new Airport("Frankfurt", 50.033306, 8.570456);
        airportsArray.add(fra);

        Airport due = new Airport("Düsseldorf", 51.280925, 6.757311);
        airportsArray.add(due);

        Airport ham = new Airport("Hamburg", 53.630389, 9.988228);
        airportsArray.add(ham);

        Airport koe = new Airport("Köln", 50.865917, 7.142744);
        airportsArray.add(koe);

        Airport mun = new Airport("München", 48.353783, 11.786086);
        airportsArray.add(mun);

        Airport ber = new Airport("Berlin", 52.362247, 13.500672);
        airportsArray.add(ber);

        Airport sfr = new Airport("San Francisco", 37.618806, -122.375417);
        airportsArray.add(sfr);

        Airport tok = new Airport("Tokyo", 35.553333, 139.781111);
        airportsArray.add(tok);

        Airport sao = new Airport("Sao Paulo", -23.426944, -47.165833);
        airportsArray.add(sao);

        Airport rio = new Airport("Rio", -22.908333, -43.196388);
        airportsArray.add(rio);

       
        objects.add(0,rio);
        objects.add(1,sao);
        objects.add(2,tok);
        objects.add(3,sfr);
        objects.add(4,ber);

    }



}
