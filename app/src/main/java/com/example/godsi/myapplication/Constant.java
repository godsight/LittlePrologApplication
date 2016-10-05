package com.example.godsi.myapplication;

/**
 * Stores the metadata of a constant
 * @author Chan Kai Ying
 * @version 0.lv
 * @createdDate 10/3/2016
 */

public class Constant extends Attribute{

    String value; //value of the constant

    //Constructor for the constant class
    public Constant(int identifier){
        super(identifier);
        value = "";
    }

}
