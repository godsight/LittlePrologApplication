package com.example.godsi.myapplication;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Chan Kai Ying
 * @version 0.lv
 * @createdDate 10/3/2016
 */

public class MainInterpreter {

    public ArrayList<Predicate> predicates;
    private ArrayList<Rule> rules;
    int programState;
    private int searchIndex;
    Predicate query;
    private GUIUpdater guiUpdater;

    public MainInterpreter(GUIUpdater gui){
        predicates = new ArrayList<>();
        rules = new ArrayList<>();
        programState = 0;
        searchIndex = 0;
        guiUpdater = gui;
    }

    public void addPredicate(Predicate pred){
        predicates.add(pred);
    }

    public boolean updatePredicate(String uiType, TextView editText){
        if (uiType.equalsIgnoreCase("Predicate")) {
            int parentId = ((View) editText.getParent().getParent()).getId();
            Predicate pred = getPredicate(parentId);
            CharSequence eType = editText.getHint();
            Editable s = editText.getEditableText();

            if ("Predicate".contentEquals(eType)) {
                if(pred.updatePredicate(s)){
                    return true;
                }
                return false;
            }

            else if("Parameter".contentEquals(eType)){
                int viewId = editText.getId();
                if(pred.updatePredicate(s, viewId)){
                    return true;
                }
                return false;
            }
        }
        return false;
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

    public boolean updateQuery(String uiType, TextView editText){
        if (uiType.equalsIgnoreCase("Query")) {
            CharSequence eType = editText.getHint();
            Editable s = editText.getEditableText();

            if ("Predicate".contentEquals(eType)) {
                if(query.updatePredicate(s)){
                    return true;
                }
                return false;
            }

            else if("Parameter".contentEquals(eType)){
                int viewId = editText.getId();
                if(query.updatePredicate(s, viewId)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * This is
     * @param tempPred
     * @return
     */
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
        if(programState == 1){
            programState = 3;
            searchIndex = 0;
        }
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

            //state 1 is ";" key
            if(state == 1 && found) {
                console = console.substring(0, console.length() - 2);
                console += ".";
                guiUpdater.showView(R.id.next);
            }

            //state 2 is "enter" key
            else if(state == 2 && found){
                console = "Yes.";
                searchIndex = 0;
                programState = 1;
                guiUpdater.hideView(R.id.next);
            }

            if (searchIndex >= predicates.size() && !found) {
                console = "No.";
                searchIndex = 0;
                programState = 1;
                guiUpdater.hideView(R.id.next);
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
