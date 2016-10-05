package com.example.godsi.myapplication;

import android.text.Editable;

/**
 * @author: Chan Kai Ying
 * @CreatedDate 4/10/16
 */

public class Variable extends Attribute{
    String name;
    String value;
    String comment;

    public Variable(int identifier){
        super(identifier);
        name = "";
        value = "";
        comment = "";
    }

    public void updateVariable(Editable s, String uiType){
        if(uiType.equalsIgnoreCase("Name")) {
            name = s.toString();
        }
        else if(uiType.equalsIgnoreCase("Value")){
            value = s.toString();
        }
    }
}
