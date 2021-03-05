package com.btdora.ebbrechtair;

import com.btdora.ebbrechtair.util.Utilities;

public class testmain {
    public static void main(String[] args) {
        Utilities u= new Utilities();

        String a = u.getIcaoByName("Frankfurt");

        System.out.println(a);
    }

}