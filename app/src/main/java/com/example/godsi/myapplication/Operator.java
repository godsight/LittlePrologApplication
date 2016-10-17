package com.example.godsi.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Chan Kai Ying
 */

public class Operator extends Attribute {

    ArrayList<Attribute> parametersArray;

    public Operator(int identifier){
        super(identifier);
        parametersArray = new ArrayList<>();
    }

    public void addOperatorParam(Attribute newAttr){
        parametersArray.add(newAttr);
    }

    public Attribute getOpAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        if(index >= 0){
            return parametersArray.get(index);
        }
        return null;
    }
}
