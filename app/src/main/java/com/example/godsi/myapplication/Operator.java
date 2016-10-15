package com.example.godsi.myapplication;

/**
 * @author: Chan Kai Ying
 */

public class Operator extends Attribute {

    String type;
    Attribute left;
    Attribute right;

    public Operator(int identifier){
        super(identifier);
    }

}
