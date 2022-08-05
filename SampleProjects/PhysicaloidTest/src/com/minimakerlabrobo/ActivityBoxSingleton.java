package com.minimakerlabrobo;

import java.util.HashMap;

public class ActivityBoxSingleton {


    private HashMap map = new HashMap();

    private static ActivityBoxSingleton atcivityBoxSingleton=null;


    private ActivityBoxSingleton(){

    }

    public HashMap getMap() {
        return map;
    }



    public static ActivityBoxSingleton getInstancia(){
           if (atcivityBoxSingleton==null){

               atcivityBoxSingleton = new ActivityBoxSingleton();

           }
           return atcivityBoxSingleton;

    }




}
