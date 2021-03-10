package com.btdora.ebbrechtAir;

import com.btdora.ebbrechtAir.classes.*;


import java.util.ArrayList;

public class Testdaten {
    ArrayList<GeoCoordinate> objects = new ArrayList<GeoCoordinate>();
    static ArrayList<Airport> airportsArray = new ArrayList<Airport>();
    static ArrayList<Fix> fixArrayList = new ArrayList<Fix>();


    public void airportsTest () {


        Airport fra = new Airport("erea","eraf",50.033306,8.570456,18000,0);
        airportsArray.add(fra);


        Airport rio = new Airport("erea","eraf",-89.908333,-120.196388,18000,0);
        airportsArray.add(rio);


        Airport ber = new Airport("erea","eraf",52.362247,13.500672,18000,0);
        airportsArray.add(ber);


        Airport sao = new Airport("erea","eraf",-23.426944,-47.165833,18000,0);
        airportsArray.add(sao);


        Airport tok = new Airport("erea","eraf",35.553333,139.781111,18000,0);
        airportsArray.add(tok);


        Airport sfr = new Airport("erea","eraf",37.618806,-122.375417,18000,0);
        airportsArray.add(sfr);

        Fix test1 = new Fix("era",56.033306,7.970456,"erasafasf","fas","fsa"  );
        Fix test2 = new Fix("era",50.033306,8.570456,"erasafasf","fas","fsa"  );

        VorDme rid = new VorDme("RID", "RIED", 112.2, 50, 8, 456, "ED");


        objects.add(0,rio);
        objects.add(1,sao);
        objects.add(2,tok);
        objects.add(3,sfr);
        objects.add(4,ber);
        objects.add(5,fra);
        objects.add(6,test1);
        objects.add(7,test2);
        objects.add(8,rid);

    }



}
