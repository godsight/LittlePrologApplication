package com.example.godsi.myapplication;

import android.text.Editable;

import java.util.ArrayList;

/**
 * @author: Chan Kai Ying
 */

public class MathematicalComputation {

    private int id;
    String name;
    ArrayList<Attribute> parametersArray;
    private String comment;

    public MathematicalComputation(int identifier){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
    }

    public int getId(){
        return id;
    }

    public void setId(int i){
        id = i;
    }

    public boolean updateMathComp(Editable s){
        if(!name.equalsIgnoreCase(s.toString())){
            name = s.toString();
            return true;
        }
        return false;
    }
}
