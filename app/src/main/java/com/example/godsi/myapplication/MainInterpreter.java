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
    Predicate query;

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

    public void updateQuery(Editable s, String uiType, EditText editText){
        if (uiType.equalsIgnoreCase("Query")) {
            CharSequence eType = editText.getHint();

            if ("Predicate".contentEquals(eType)) {
                query.updatePredicate(s);
            }

            else if("Parameter".contentEquals(eType)){
                int viewId = editText.getId();
                query.updatePredicate(s, viewId);
            }
        }
    }

    public boolean querySearch(Predicate tempPred){
        for(int i = 0; i < predicates.size(); i++){
            Predicate p = predicates.get(i);
            if(p.parametersArray.size() == tempPred.parametersArray.size()){
                if(p.name.equalsIgnoreCase(tempPred.name)){
                    boolean match = true;
                    for(int j = 0; j < p.parametersArray.size(); j++){
                        Constant param1 = (Constant) p.parametersArray.get(j);
                        Constant param2 = (Constant) tempPred.parametersArray.get(j);
                        if(!param1.value.equalsIgnoreCase(param2.value)){
                            match = false;
                            break;
                        }
                    }
                    if(match){
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public String variableSearch(Predicate tempPred, int state) {
        if (programState == 3) {
            boolean found = false;
            String console = "";
            while (searchIndex < predicates.size() && !found) {
                boolean match = true;
                Predicate p = predicates.get(searchIndex);
                if (tempPred.parametersArray.size() != p.parametersArray.size()) {
                    match = false;
                } else {
                    for (int i = 0; i < tempPred.parametersArray.size(); i++) {
                        Constant param1 = (Constant) p.parametersArray.get(i);
                        Constant param2 = (Constant) tempPred.parametersArray.get(i);
                        if (!isVariable(param2.value)) {
                            if (param1.value.equalsIgnoreCase(param2.value)) {
                                match = true;
                            } else {
                                match = false;
                                break;
                            }
                        } else {
                            console = param2.value + " = " + param1.value + ", ";
                        }
                    }
                }
                if (match) {
                    found = true;
                }
                searchIndex++;
            }

            if(state == 1) {
                console = console.substring(0, console.length() - 2);
                console += ".";
            }
            else if(state == 2){
                console = "Yes.";
                searchIndex = 0;
                programState = 1;
            }

            if (searchIndex > predicates.size() && !found) {
                console = "No.";
                searchIndex = 0;
                programState = 1;
            }
            return console;
        }
        return null;
    }

    private boolean isVariable(String s){
        char first = s.charAt(0);
        if(Character.isUpperCase(first) || first == '_'){
            return true;
        }
        return false;
    }
}
