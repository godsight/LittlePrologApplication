package com.example.godsi.myapplication;

import android.text.Editable;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * @author Chan Kai Ying
 * @version 0.lv
 * @createdDate 10/3/2016
 */

public class Predicate {

    private int id;
    String name;
    private ArrayList<Attribute> parametersArray;
    private ArrayList<Rule> rulesArray;
    private String comment;

    public Predicate(int identifier){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
        rulesArray = new ArrayList<>();
        comment = "";
    }

    public Predicate(int identifier, Attribute param){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
        parametersArray.add(param);
        rulesArray = new ArrayList<>();
        comment = "";
    }

    public Attribute getParameter(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        return parametersArray.get(index);
    }

    public void addAttribute(Attribute newAttr){
        parametersArray.add(newAttr);
    }


    public void updatePredicate(Editable s){
        name = s.toString();
    }


    public void updatePredicate(Editable s, int viewId){
        Constant cons = (Constant) getParameter(viewId);
        cons.value = s.toString();
    }

    @Override
    //Overriding equals() to compare two Predicate objects
    public boolean equals(Object p){
        //if the object is compared with itself then return true
        if(p == this){
            return true;
        }

        //check if p is an instance of Predicate
        if(!(p instanceof Predicate)){
            return false;
        }

        //Typecast p to Predicate so that we can compare data members
        Predicate other = (Predicate) p;
        if(this.id == other.id){
            return true;
        }

        return false;
    }

    @Override
    //Overriding Hashcode() to compare two Predicate objects
    public int hashCode(){
        int hash = 17;
        hash = 31 * hash + id;
        return hash;
    }
}
