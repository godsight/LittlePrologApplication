package com.example.godsi.myapplication;

import android.text.Editable;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class stores the metadata of a predicate
 * @author Chan Kai Ying && Hong Chin Choong
 * @version 0.lv
 * @createdDate 10/3/2016
 */

public class Predicate implements Writable{

    private int id; //identifier for the predicate
    String name; //name of the predicate
    public int nameId; //identifier for predicate name
    ArrayList<Attribute> parametersArray; //stores the parameters of the predicate
    private String comment; //comment for the predicate
    boolean isValid; //whether predicate has valid parameters

    /**
     * get predicate's Id
     * @return interger as Id
     */
    public int getId(){
        return id;
    }

    /**
     * Set predicate's Id
     * @param i, integer as id
     */
    public void setId(int i){
        id = i;
    }

    /**
     * Constructor for predicate object with given Id
     * @param identifier, integer as Id
     * @return Predicate object
     */
    public Predicate(int identifier){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
        comment = "";
        isValid = false;
    }

    /**
     * Constructor for predicate object with given Id and constant object added in parameter
     * @param identifier, integer as Id
     * @param param, Constant object to add into parametersArray
     */
    public Predicate(int identifier, Attribute param){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
        parametersArray.add(param);
        comment = "";
        isValid = false;
    }

    /**
     * Returns the parameters of the predicate with given Id
     * @param id, integer as Id
     * @return Attribute object
     */
    public Attribute getParameter(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        if(index >= 0) {
            return parametersArray.get(index);
        }
        return null;
    }

    /**
     * Adds a new attribute to the parameters array
     * @param newAttr, Attribute object
     */
    public void addAttribute(Attribute newAttr){
        parametersArray.add(newAttr);
        setValidity();
    }

    /**
     * Removes the attribute from the parameter array based on id
     * @param id, integer as Id
     */
    public void deleteAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        parametersArray.remove(index);
        setValidity();
    }

    /**
     * updates the name of the predicate
     * @param s, string as name
     * @return boolean: true if changes made
     */
    public boolean updatePredicate(Editable s){
        if(!name.equalsIgnoreCase(s.toString())) {
            name = s.toString();
            return true;
        }
        return false;
    }

    /**
     * updates the parameter of the predicate
     * @param s, string of new changes
     * @param viewId, view's Id
     * @return boolean: true if changes has been made
     */
    public boolean updatePredicate(Editable s, int viewId){
        Constant cons = (Constant) getParameter(viewId);
        if(cons != null && !cons.value.equalsIgnoreCase(s.toString())) {
            cons.value = s.toString();
            setValidity();
            return true;
        }
        return false;
    }

    /**
     * checks whether the all existing predicates are valid
     * @return boolean: true if all predicates are valid
     */
    private boolean checkValidity(){
        boolean valid = true;
        int index = 0;
        while(valid && index < parametersArray.size()){
            Constant constant = (Constant) parametersArray.get(index);
            if(constant.value.equalsIgnoreCase("")){
                valid = false;
            }
            index++;
        }
        return valid;
    }

    /**
     * check which search functions should be used for interpreting query predicate, querySearch or variableSearch
     * querySearch = 1, variableSearch = 2
     * @return integer indicating which search to use
     */
    public int queryOrVariableSearch(){
        char first;
        for(Attribute param:parametersArray){
            Constant c = (Constant) param;
            first = c.value.charAt(0);

            //first character of constant value is in uppercase or equals to "_"
            if(Character.isUpperCase(first) || first == '_'){
                //do variableSearch
                return 2;
            }
        }
        //do querySearch
        return 1;
    }

    /**
     * set isValid class variable to true after checking whether all existing predicates are valid, set false otherwise
     */
    public void setValidity(){
        if(checkValidity()){
            isValid = true;
        }
        else{
            isValid = false;
        }
    }

    /**
     * Serializes the data stored in this class into a string
     * @return string to represent data stored in this class
     */
    public ArrayList<String> serialize(){
        ArrayList<String> serializedArray = new ArrayList<>();
        String line = name + "(";
        for (Attribute parameter: parametersArray
             ) {
            line += parameter.value;
            line += ",";
        }
        line = line.substring(0,line.length()-1);
        line += ").";
        serializedArray.add(line);
        return  serializedArray;
    }
}
