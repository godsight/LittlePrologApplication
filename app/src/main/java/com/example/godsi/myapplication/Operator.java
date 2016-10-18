package com.example.godsi.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Chan Kai Ying
 */

public class Operator extends Attribute implements Writable {

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

    /**
     * Serializes the info of the class into a string
     */
    public ArrayList<String> serialize () {
        ArrayList<String> serializedArray = new ArrayList<>();
        String line = "";
        for (Attribute parameter:parametersArray
             ) {
            line += parameter.value + " ";
        }
        serializedArray.add(line.substring(0,line.length()-1));
        return serializedArray;
    }
}
