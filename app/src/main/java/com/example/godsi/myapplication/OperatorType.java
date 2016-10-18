package com.example.godsi.myapplication;

/**
 * @author: Chan Kai Ying
 */

public class OperatorType extends Attribute{

    //Constructor for the constant class
    public OperatorType(int identifier){
        super(identifier);
        value = ""; //value of the operatorType
    }

    public String comparisonOrExpression(){
        if(value.equalsIgnoreCase("==") || value.equalsIgnoreCase(">") || value.equalsIgnoreCase("<") || value.equalsIgnoreCase(">=") ||
                value.equalsIgnoreCase("<=") || value.equalsIgnoreCase("=")) {
            return "Comparison";
        }
        return "Expression";
    }

}