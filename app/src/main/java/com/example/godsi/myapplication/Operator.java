package com.example.godsi.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Stores the metadata for Operator
 * @author: Chan Kai Ying && Hong Chin Choong
 */

public class Operator extends Attribute implements Writable {

    ArrayList<Attribute> parametersArray; //arraylist of parameters

    /**
     * Constructor for Operator object
     * @param identifier
     */
    public Operator(int identifier){
        super(identifier);
        parametersArray = new ArrayList<>();
    }

    /**
     * Add new parameter into Operator
     * @param newAttr, Attribute object
     */
    public void addOperatorParam(Attribute newAttr){
        parametersArray.add(newAttr);
    }

    /**
     * Get parameter of Operator with given Id
     * @param id, integer as Id
     * @return Attribute object
     */
    public Attribute getOpAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        if(index >= 0){
            return parametersArray.get(index);
        }
        return null;
    }

    /**
     * Convert operator's parameters into string format. Replace variable with stored value in hashmap arraylist
     * For example, 12 < 3 + 4
     * @param queryRuleVariables, hashmap arraylist
     * @return String
     */
    public String convertOperatorToString(ArrayList<Map<String, String>> queryRuleVariables) {
       String attributes = "";
            for (int i = 0; i < parametersArray.size(); i++) {

                //if parameter is a Constant object
                if (parametersArray.get(i) instanceof Constant) {
                    Constant c = (Constant) parametersArray.get(i);
                    String regex = "[0-9]+";

                    //check if constant value is an integer
                    if (c.value.matches(regex)) {
                        attributes += c.value;
                    }

                    //if constant value is a variable, get value from hashmap arraylist using constant value as key
                    else {
                        Map<String, String > m = getQueryRuleVariable(c.value, queryRuleVariables);
                        attributes += m.get(c.value);
                    }
                }

                //if parameter is an OperatorType object
                else{
                    OperatorType operatorType = (OperatorType) parametersArray.get(i);
                    attributes += operatorType.value;
                }
            }
        return attributes;
    }

    /**
     * Get HashMap object with matched key provided
     * @param key, String as key
     * @param queryRuleVariables, HashMap arraylist
     * @return HashMap object
     */
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
