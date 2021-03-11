package com.btdora.ebbrechtair;

import com.btdora.ebbrechtair.classes.*;

import java.util.ArrayList;

public class Testdaten {
    static ArrayList<GeoCoordinate> airportsArray = new ArrayList<GeoCoordinate>();

    public void airportsTest () {
//        Airport fra = new Airport("Frankfurt", 50.033306, 8.570456);
//        airportsArray.add(fra);
//
//        Airport due = new Airport("Düsseldorf", 51.280925, 6.757311);
//        airportsArray.add(due);
//
//        Airport ham = new Airport("Hamburg", 53.630389, 9.988228);
//        airportsArray.add(ham);
//
//        Airport koe = new Airport("Köln", 50.865917, 7.142744);
//        airportsArray.add(koe);
//
//        Airport mun = new Airport("München", 48.353783, 11.786086);
//        airportsArray.add(mun);
//
//        Airport ber = new Airport("Berlin", 52.362247, 13.500672);
//        airportsArray.add(ber);
//
//        Airport sfr = new Airport("San Francisco", 37.618806, -122.375417);
//        airportsArray.add(sfr);
//
//        Airport tok = new Airport("Tokyo", 35.553333, 139.781111);
//        airportsArray.add(tok);
//
       Airport sao = new Airport("Sao Paulo", -23.426944, -47.165833);



        Fix test1 = new Fix("era",56.033306,7.970456,"erasafasf","fas","fsa"  );
        Fix test2 = new Fix("era",50.033306,8.570456,"erasafasf","fas","fsa"  );

        Navaid rid = new VorDme("RID", "RIED", 112.2, 50, 8, 456, "ED");
        Navaid ris = new Vor("RID", "RIED", 112.2, 51, 8, 456, "ED");
        Navaid riy = new Dme("RID", "RIED", 112.2, 52, 8.8, 456, "ED");


        airportsArray.add(0,test1);
        airportsArray.add(1,test2);
        airportsArray.add(2,rid);
        airportsArray.add(3,ris);
        airportsArray.add(4,riy);
        airportsArray.add(5,sao);
    }

}
