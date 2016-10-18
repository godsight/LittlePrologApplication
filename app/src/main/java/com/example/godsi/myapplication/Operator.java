package com.example.godsi.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    public String convertOperatorToString(ArrayList<Map<String, String>> queryRuleVariables) {
       String attributes = "";
            for (int i = 0; i < parametersArray.size(); i++) {
                if (parametersArray.get(i) instanceof Constant) {
                    Constant c = (Constant) parametersArray.get(i);
                    String regex = "[0-9]+";
                    if (c.value.matches(regex)) {
                        attributes += c.value;
                    }
                    else {
                        Map<String, String > m = getQueryRuleVariable(c.value, queryRuleVariables);
                        attributes += m.get(c.value);
                    }
                }
                else{
                    OperatorType operatorType = (OperatorType) parametersArray.get(i);
                    attributes += operatorType.value;
                }
            }
        return attributes;
    }

    private Map<String, String> getQueryRuleVariable(String key, ArrayList<Map<String , String>> queryRuleVariables){
        for(Map<String, String> m : queryRuleVariables){
            if(m.containsKey(key)){
                return m;
            }
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
