package com.btdora.ebbrechtair;
import com.btdora.ebbrechtair.classes.Airport;
import com.btdora.ebbrechtair.util.Utilities;

import java.util.List;

public class testmain {
    public static void main(String[] args) {
        Utilities u= new Utilities();

     List<Airport> a =  u.getIcaoByName("Frankfurt");

        System.out.println(a);
    }

}