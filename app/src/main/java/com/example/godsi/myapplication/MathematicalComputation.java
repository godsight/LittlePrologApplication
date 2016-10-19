package com.example.godsi.myapplication;

import android.graphics.Path;
import android.text.Editable;

import java.util.ArrayList;

/**
 * Stores metadata for mathematical computations
 * @author: Hong Chin Choong & Chan Kai Ying
 */

public class MathematicalComputation implements Writable {

    private int id; //Id
    public int mathCompEditid;
    String name; //name
    ArrayList<Attribute> parametersArray; //parameters of mathematical computations
    private String comment; //comment attached to mathematical computation object

    /**
     * Constructor for an empty mathematical computation object
     */
    public MathematicalComputation(){
        name = "";
        parametersArray = new ArrayList<>();
    }

    /**
     * Constructor for mathematical computation object with Id
     * @param identifier, integer as Id
     */
    public MathematicalComputation(int identifier){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
    }

    /**
     * Get mathematical computation object's Id
     * @return int Id
     */
    public int getId(){
        return id;
    }

    /**
     * Set mathematical computation object's Id
     * @param i, integer as Id
     */
    public void setId(int i){
        id = i;
    }

    /**
     * Update mathematical computation's name value
     * @param s, string as name
     * @return boolean: True if there are changes made
     */
    public boolean updateMathComp(String s){
        //if name is not equal to current value
        if(!name.equalsIgnoreCase(s)){
            name = s;
            return true;
        }
        return false;
    }

    /**
     * Update mathematical computation object's parameters values
     * @param s, String as new string
     * @param argType, type of element change
     * @param viewId, integer as Id of view
     * @param parentId, integer as view's parent Id
     * @return boolean: true if there are changes made
     */
    public boolean updateMathComp(String s, String argType, int viewId, int parentId){

        //For read object parameter
        if(argType.equalsIgnoreCase("readArg")){
            Read read = (Read) getAttribute(viewId);
            if(read != null && !read.value.equalsIgnoreCase(s)){
                read.value = s;
                return true;
            }
            return false;
        }

        //For write object parameter
        else if(argType.equalsIgnoreCase("writeArg")){
            Write write = (Write) getAttribute(viewId);
            if(write != null && !write.value.equalsIgnoreCase(s)){
                write.value = s;
                return true;
            }
            return false;
        }

        //For constant object parameter in operator object
        else if(argType.equalsIgnoreCase("val")){
            Operator parentOp = (Operator) getAttribute(parentId);
            Constant constant = (Constant) parentOp.getOpAttribute(viewId);
            if(constant != null && !constant.value.equalsIgnoreCase(s)){
                constant.value = s;
                return true;
            }
            return false;
        }

        //For Operator Type object parameter in operator object
        else if(argType.equalsIgnoreCase("OP")){
            Operator parentOp = (Operator) getAttribute(parentId);
            OperatorType operatorType = (OperatorType) parentOp.getOpAttribute(viewId);
            if(operatorType != null){
                operatorType.value = s;
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Add new parameters into mathematical rule as parameter
     * @param arg, attribute object as parameter for mathematical rule
     */
    public void addParam(Attribute arg){
        parametersArray.add(arg);
    }

    /**
     * Get mathematical computation object's parameter by given Id
     * @param id, integer
     * @return Attribute object which can either be Read, Write or Operator objects
     */
    public Attribute getAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        if(index >= 0){
            return parametersArray.get(index);
        }
        return null;
    }

    /**
     * Removes the attribute from the parameter array based on id
     * @param id, integer as id
     */
    public void deleteAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        if(index >= 0) {
            parametersArray.remove(index);
        }
    }

    /**
     * Serializes the info of the class into a string
     */
    public ArrayList<String> serialize () {
        ArrayList<String> serializedArray = new ArrayList<>();
        String line = name + ":-";
        for (Attribute parameter: parametersArray
                ) {
            if (parameter instanceof Read){
                line += "read(" + parameter.value + "),";
            }
            else if (parameter instanceof Write){
                line += "write(" + parameter.value + "),";
            }
            else if (parameter instanceof Operator){
                ArrayList<String> stringArrayList = ((Operator) parameter).serialize();
                line += stringArrayList.get(0) + ",";
            }
        }
        line = line.substring(0,line.length()-1);
        line += ".";
        serializedArray.add(line);
        return  serializedArray;
    }
}
