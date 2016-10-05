package com.example.godsi.myapplication;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Chan Kai Ying
 * @version 0.lv
 * @createdDate 10/3/2016
 */

public class MainInterpreter {

    private ArrayList<Predicate> predicates;
    private ArrayList<Rule> rules;
    private ArrayList<Variable> variables;
    int programState;
    private int searchIndex;

    public MainInterpreter(){
        predicates = new ArrayList<>();
        rules = new ArrayList<>();
        variables = new ArrayList<>();
        programState = 0;
        searchIndex = 0;
    }

    public void addPredicate(Predicate pred){
        predicates.add(pred);
    }

    public void updatePredicate(Editable s, String uiType, EditText editText){
        if (uiType.equalsIgnoreCase("Predicate")) {
            int parentId = ((View) editText.getParent().getParent()).getId();
            Predicate pred = getPredicate(parentId);
            CharSequence eType = editText.getHint();

            if ("Predicate".contentEquals(eType)) {
                pred.updatePredicate(s);
            }

            else if("Parameter".contentEquals(eType)){
                int viewId = editText.getId();
                pred.updatePredicate(s, viewId);
            }
        }
    }

    private Integer getPredicateIndex(int id){
        for(int i = 0; i < predicates.size(); i++){
            Predicate temp = predicates.get(i);
            if(temp.getId() ==  id){
                return i;
            }
        }
        return null;
    }

    public Predicate getPredicate(int id){
        Integer index = getPredicateIndex(id);
        if(index != null){
            return predicates.get(index);
        }
        return null;
    }

    public void deletePredicate(int id){
        Integer index = getPredicateIndex(id);
        if(index != null){
            predicates.remove(index);
        }
    }

    public void addVariable(Variable var){
        variables.add(var);
    }

    public void updateVariable(Editable s, EditText editText){
        String type = editText.getHint().toString();
        int parentId = ((LinearLayout) editText.getParent()).getId();
        Variable var = getVariable(parentId);
        var.updateVariable(s, type);
    }

    public Variable getVariable(int id){
        int index = variables.indexOf(new Variable(id));
        return variables.get(index);
    }

    public void deleteVariable(int id){
        int index = variables.indexOf(new Variable(id));
        variables.remove(index);
    }

    public boolean runInterpreter(int stateId){
        if(checkComponentsValidity()){
            programState = stateId;
            return true;
        }
        return false;
    }

    public void stopInterpreter(int stateId){
        programState = stateId;
    }

    private boolean checkComponentsValidity(){
        boolean valid = true;
        int index = 0;
        while(valid && index < predicates.size()){
            Predicate predicate = predicates.get(index);
            if(!predicate.isValid){
                valid = false;
            }
            index++;
        }
        return valid;
    }

    public boolean querySearch(Predicate tempPred){
        if(predicates.contains(tempPred)){
            return true;
        }
        return false;
    }

}
