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

    public Integer findComparison(){
        for(int i = 0; i < parametersArray.size(); i++){
            if(parametersArray.get(i) instanceof OperatorType){
                OperatorType operatorType = (OperatorType) parametersArray.get(i);
                if(operatorType.comparisonOrExpression().equalsIgnoreCase("Comparison")){
                    return i;
                }
            }
        }
        return null;
    }

    public String convertOperatorToString(String side, int comparisonIndex){
        String result = "";
        if(side.equalsIgnoreCase("Left")){
            for(int i = 0; i < comparisonIndex; i++){
                if(parametersArray.get(i) instanceof Constant){
                    Constant c = (Constant) parametersArray.get(i);
                    String regex = "[0-9]+";
                    if(c.value.matches(regex)){
                        result += c.value;
                    }
                    else{

                    }
                }
            }
        }
        return result;
    }
}
