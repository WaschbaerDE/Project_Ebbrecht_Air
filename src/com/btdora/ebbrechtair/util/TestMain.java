package com.btdora.ebbrechtair.util;
import com.btdora.ebbrechtair.classes.GeoCoordinate;

import java.util.ArrayList;

import javax.swing.text.Utilities;

public class TestMain {

    public static void main(String [] Args){

        DataGrid a = new DataGrid();

        ArrayList<GeoCoordinate> result = a.get(50, 8);
        System.out.println(result);



    }
}
