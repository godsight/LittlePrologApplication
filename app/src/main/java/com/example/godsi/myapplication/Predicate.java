package com.example.godsi.myapplication;

import android.text.Editable;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * This class stores the metadata of a predicate
 * @author Chan Kai Ying
 * @version 0.lv
 * @createdDate 10/3/2016
 */

public class Predicate {

    private int id; //identifier for the predicate
    String name; //name of the predicate
    ArrayList<Attribute> parametersArray; //stores the parameters of the predicate
    private ArrayList<Rule> rulesArray; //stores the rules related to the predicate
    private String comment; //comment for the predicate
    boolean isValid; //whether predicate has valid parameters

    //returns the identifier for the predicate
    public int getId(){
        return id;
    }

    //Constructor for the predicate
    public void setId(int i){
        id = i;
    }


    public Predicate(int identifier){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
        rulesArray = new ArrayList<>();
        comment = "";
        isValid = false;
    }

    //Constructor for the predicate
    public Predicate(int identifier, Attribute param){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
        parametersArray.add(param);
        rulesArray = new ArrayList<>();
        comment = "";
        isValid = false;
    }

    //Returns the parameters of the predicate
    public Attribute getParameter(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        return parametersArray.get(index);
    }

    //Adds a new attribute to the parameters array
    public void addAttribute(Attribute newAttr){
        parametersArray.add(newAttr);
        setValidity();
    }

    //Removes the attribute from the parameter array based on id
    public void deleteAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        parametersArray.remove(index);
        setValidity();
    }

    //updates the name of the predicate
    public void updatePredicate(Editable s){
        name = s.toString();
    }

    //updates the parameter of the predicate
    public void updatePredicate(Editable s, int viewId){
        Constant cons = (Constant) getParameter(viewId);
        cons.value = s.toString();
        setValidity();
    }

    //checks whether the predicate is valid
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


    //sets the validity of the predicate
    public int queryOrVariableSearch(){
        char first;
        for(Attribute param:parametersArray){
            Constant c = (Constant) param;
            first = c.value.charAt(0);
            if(Character.isUpperCase(first)){
                return 2;
            }
        }
        return 1;
    }


    private void setValidity(){
        if(checkValidity()){
            isValid = true;
        }
        else{
            isValid = false;
        }
    }
}
