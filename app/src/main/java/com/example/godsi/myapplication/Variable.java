package com.example.godsi.myapplication;

import android.text.Editable;

/**
 * This class stores the variable's name and its values as well as comments
 * @author: Chan Kai Ying
 * @CreatedDate 4/10/16
 */

public class Variable extends Attribute{
    String name; //name of the variable
    String value; //value of the variable
    String comment; //comment for the variable

    public Variable(int identifier){
        super(identifier);
        name = "";
        value = "";
        comment = "";
    }

    //updates the name/value of the variable
    public void updateVariable(Editable s, String uiType){
        if(uiType.equalsIgnoreCase("Name")) {
            name = s.toString();
        }
        else if(uiType.equalsIgnoreCase("Value")){
            value = s.toString();
        }
    }
}
