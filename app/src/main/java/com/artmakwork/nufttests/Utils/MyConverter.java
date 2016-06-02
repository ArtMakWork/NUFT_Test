package com.artmakwork.nufttests.Utils;


import com.artmakwork.nufttests.POJO.Group;
import com.artmakwork.nufttests.POJO.MyTest;
import com.artmakwork.nufttests.POJO.Thema;

import java.util.ArrayList;

public class MyConverter {

    public static String[] arrListGroupToStringMass(ArrayList<Group> arrayList){

        String [] strMass = new String[arrayList.size()];

        for (int j = 0;j<arrayList.size();j++){
            strMass[j] = arrayList.get(j).toString();
        }

        return strMass;
    }

    public static String[] arrListThemaToStringMass(ArrayList<Thema> arrayList){

        String [] strMass = new String[arrayList.size()];

        for (int j = 0;j<arrayList.size();j++){
            strMass[j] = arrayList.get(j).toString();
        }

        return strMass;
    }

    public static String[] arrListMyTestToStringMass(ArrayList<MyTest> arrayList){

        String [] strMass = new String[arrayList.size()];

        for (int j = 0;j<arrayList.size();j++){
            strMass[j] = arrayList.get(j).toString();
        }

        return strMass;
    }
}
