package com.example.godsi.myapplication;

import android.text.Editable;
import android.view.View;
import android.widget.TextView;
import com.udojava.evalex.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chan Kai Ying
 * @version 0.lv
 * @createdDate 10/3/2016
 * @description This class is the main class used for backend operations including saving predicates and mathematical computations,
 *                  interpreting query and etc.
 */

public class MainInterpreter {

    public ArrayList<Predicate> predicates;
    public ArrayList<MathematicalComputation> mathComputations;
    int programState;
    int searchIndex;
    Predicate queryPredicate;
    MathematicalComputation queryRule;
    boolean queryRuleCondition;
    private GUIUpdater guiUpdater;
    public MetaInfo metaInfo = new MetaInfo();
    ArrayList<Map<String, String>> queryRuleVariables;

    /**
     * @desciption Constructor for mainInterpreter class for initialization
     * @param gui : Front end UI manager
     */
    public MainInterpreter(GUIUpdater gui){
        predicates = new ArrayList<>();
        mathComputations = new ArrayList<>();
        programState = 0;
        searchIndex = 0;
        guiUpdater = gui;
        queryRuleCondition = true;
        queryRuleVariables = new ArrayList<>();
    }

    /**
     * @description Start interpreter
     * @param stateId: an integer
     * @return true when interpreter successfully started.
     */
    public boolean runInterpreter(int stateId){
        if(checkComponentsValidity()){
            programState = stateId;
            return true;
        }
        return false;
    }

    /**
     * @description STop interpreter
     * @param stateId: an integer
     */
    public void stopInterpreter(int stateId){
        programState = stateId;
        queryPredicate = null;
        guiUpdater.hideView(R.id.enter);
        guiUpdater.hideView(R.id.next);
    }

    /**
     * @description Get predicate by Id
     * @param id, integer
     * @return
     */
    public Predicate getPredicate (int id){
        for (Predicate predicate:predicates
             ) {
            if (predicate.getId() == id){
                return predicate;
            }
        }
        return null;
    }

    /**
     * @description Add predicate into predicates arraylist
     * @param pred, Predicate object
     */
    public void addPredicate(Predicate pred){
        predicates.add(pred);
    }

    /**
     * Update selected predicate in predicates arraylist
     * @param uiType: element to be operated
     * @param editText: textview that has changes made
     * @return
     */
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

    /**
     * @description Get predicate's index in predicates arraylist with given predicate Id
     * @param id: an Integer
     * @return Integer indicating the index of predicate in predicates arraylist
     */
    private Integer getPredicateIndex(int id){
        for(int i = 0; i < predicates.size(); i++){
            Predicate temp = predicates.get(i);
            if(temp.getId() ==  id){
                return i;
            }
        }
        return null;
    }

    /**
     * @description Delete predicate from predicates arraylist with given predicate Id
     * @param id: an Integer
     */
    public void deletePredicate(int id){
        Integer index = getPredicateIndex(id);
        if(index != null){
            Predicate temp = predicates.get(index);
            predicates.remove(temp);
        }
    }

    /**
     * @description Add new mathematicalComputaion object into mathComputations arraylist
     * @param mathematicalComputation, mathematicalComputation object
     */
    public void addMathComp(MathematicalComputation mathematicalComputation){
        mathComputations.add(mathematicalComputation);
    }

    /**
     * @description Get mathematicalComputation object from mathComputations arraylist with given id
     * @param id, an integer
     * @return MathematicalComputation object with given id
     */
    public MathematicalComputation getMathComp(int id){
        for(MathematicalComputation mathComp: mathComputations ){
            if(mathComp.getId() == id){
                return mathComp;
            }
        }
        return null;
    }

    /**
     * @description Get MathematicalComputation object from mathComputation arraylist with given name
     * @param name
     * @return
     */
    public MathematicalComputation getMathComp(String name){
        for(MathematicalComputation mathComp: mathComputations){
            if(mathComp.name.equalsIgnoreCase(name)){
                return mathComp;
            }
        }
        return null;
    }

    public boolean updateMathComp(String uiType, TextView editText){
        if(uiType.equalsIgnoreCase("MathematicalRule") || uiType.equalsIgnoreCase("Query Rule")) {
            String eType = editText.getHint().toString();
            String s = editText.getText().toString();

            if ("MathematicalRule".contentEquals(eType)) {
                MathematicalComputation mathematicalComputation = getCurrentMathComp(uiType, editText);
                if(mathematicalComputation != null) {
                    if (mathematicalComputation.updateMathComp(s)) {
                        return true;
                    }
                }
                return false;
            }
            else {
                int parentId = ((View) editText.getParent().getParent()).getId();
                MathematicalComputation mathematicalComputation = getMathComp(parentId);
                if(mathematicalComputation != null) {
                    int viewId = editText.getId();
                    int opId = ((View) editText.getParent()).getId();
                    if (mathematicalComputation.updateMathComp(s, eType, viewId, opId)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private MathematicalComputation getCurrentMathComp(String uiType, TextView editText){
        View outerParent = (View) editText.getParent().getParent();
        if (outerParent.getId() == R.id.playground || outerParent.getId() == R.id.playgroundContainer) {
            int parentId = ((View) editText.getParent()).getId();
            return getMathComp(parentId);
        } else if (outerParent.getId() == R.id.consoleCommandLine) {
            return queryRule;
        }
        return null;
    }

    public void deleteMathComp(int id){
        Integer index = getMathCompIndex(id);
        if(index != null){
            MathematicalComputation temp = mathComputations.get(index);
            mathComputations.remove(temp);
        }
    }

    private Integer getMathCompIndex(int id){
        for(int i = 0; i < mathComputations.size(); i++){
            MathematicalComputation temp = mathComputations.get(i);
            if(temp.getId() ==  id){
                return i;
            }
        }
        return null;
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
        if (uiType.equalsIgnoreCase("Query Predicate")) {
            CharSequence eType = editText.getHint();
            Editable s = editText.getEditableText();

            if ("Predicate".contentEquals(eType)) {
                if(queryPredicate.updatePredicate(s)){
                    return true;
                }
                return false;
            }

            else if("Parameter".contentEquals(eType)){
                int viewId = editText.getId();
                if(queryPredicate.updatePredicate(s, viewId)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean updateQueryRuleVariable(String key, String value){
        int index = 0;

        while(index < queryRuleVariables.size()){
            Map<String, String> m = queryRuleVariables.get(index);
            if(m.containsKey(key)){
                m.put(key, value);
                 return true;
            }
            index ++;
        }
        return false;
    }

    private Map<String, String> getQueryRuleVariable(String key){
        for(Map<String, String> m : queryRuleVariables){
            if(m.containsKey(key)){
                return m;
            }
        }
        return null;
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

    public void interpretMathRule(){
        guiUpdater.hideView(R.id.interpret);
        if(programState == 1){
            programState = 4;
            searchIndex = 0;
        }

        if(programState == 4) {
            MathematicalComputation curRule = getMathComp(queryRule.name);
            while (searchIndex < curRule.parametersArray.size() && queryRuleCondition) {
                if (curRule.parametersArray.get(searchIndex) instanceof Read) {
                    Read r = (Read) curRule.parametersArray.get(searchIndex);
                    Map<String, String> m = new HashMap<>();
                    m.put(r.value, "");
                    queryRuleVariables.add(m);
                    String header = ((TextView) guiUpdater.getLastConsoleLog()).getText().toString();
                    guiUpdater.updateReadInput(header, r.value);
                    guiUpdater.showView(R.id.input);
                    break;
                } else if (curRule.parametersArray.get(searchIndex) instanceof Write) {
                    Write w = (Write) curRule.parametersArray.get(searchIndex);
                    String[] temp = w.value.split("");
                    if (!(temp.length > 1) && isVariable(w.value)) {
                        Map<String, String> m = getQueryRuleVariable(w.value);
                        guiUpdater.createConsoleLog(w.value + " = " + m.get(w.value));
                    } else {
                        guiUpdater.createConsoleLog(w.value);
                    }
                    searchIndex++;
                } else {
                    Operator op = (Operator) curRule.parametersArray.get(searchIndex);
                    String expression = op.convertOperatorToString(queryRuleVariables);
                    Expression e = new Expression(expression);
                    if (e.eval().toString().equalsIgnoreCase("0")) {
                        queryRuleCondition = false;
                    }
                    searchIndex++;
                }
            }

            if(searchIndex >= curRule.parametersArray.size()){
                searchIndex = 0;
                programState = 1;
                queryRuleVariables = null;
                queryRuleVariables = new ArrayList<>();

                if(queryRuleCondition){
                    guiUpdater.createConsoleLog("Yes");
                }
                else{
                    guiUpdater.createConsoleLog("No");
                }
            }
        }
    }

}
