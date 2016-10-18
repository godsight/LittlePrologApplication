package com.example.godsi.myapplication;

/**
 * @author: Chan Kai Ying
 */

public class OperatorType extends Attribute{

    String value; //value of the constant

    //Constructor for the constant class
    public OperatorType(int identifier){
        super(identifier);
        value = "";
    }

    public String comparisonOrExpression(){
        if(value.equalsIgnoreCase("==") || value.equalsIgnoreCase(">") || value.equalsIgnoreCase("<") || value.equalsIgnoreCase(">=") ||
                value.equalsIgnoreCase("<=") || value.equalsIgnoreCase("=")) {
            return "Comparison";
        }
        return "Expression";
    }

}