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
    ArrayList<Attribute> parametersArray;
    private ArrayList<Rule> rulesArray;
    private String comment;
    boolean isValid;

    public int getId(){
        return id;
    }

    public Predicate(int identifier){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
        rulesArray = new ArrayList<>();
        comment = "";
        isValid = false;
    }

    public Predicate(int identifier, Attribute param){
        id = identifier;
        name = "";
        parametersArray = new ArrayList<>();
        parametersArray.add(param);
        rulesArray = new ArrayList<>();
        comment = "";
        isValid = false;
    }

    public Attribute getParameter(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        return parametersArray.get(index);
    }

    public void addAttribute(Attribute newAttr){
        parametersArray.add(newAttr);
        setValidity();
    }

    public void deleteAttribute(int id){
        int index = parametersArray.indexOf(new Attribute(id));
        parametersArray.remove(index);
        setValidity();
    }

    public void updatePredicate(Editable s){
        name = s.toString();
    }

    public void updatePredicate(Editable s, int viewId){
        Constant cons = (Constant) getParameter(viewId);
        cons.value = s.toString();
        setValidity();
    }

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

    private void setValidity(){
        if(checkValidity()){
            isValid = true;
        }
        else{
            isValid = false;
        }
    }
}
