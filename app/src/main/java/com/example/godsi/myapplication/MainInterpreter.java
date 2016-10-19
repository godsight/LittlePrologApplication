package com.example.godsi.myapplication;

import android.text.Editable;
import android.view.View;
import android.widget.TextView;
import com.udojava.evalex.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hong Chin Choong & Chan Kai Ying
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
     * @param name, String
     * @return MathematicalComputation object with given name
     */
    public MathematicalComputation getMathComp(String name){
        for(MathematicalComputation mathComp: mathComputations){
            if(mathComp.name.equalsIgnoreCase(name)){
                return mathComp;
            }
        }
        return null;
    }

    /**
     * @description Update MathComputation object.
     * @param uiType, String
     * @param editText, TextView
     * @return boolean: True when update successfully; False when update unsuccessful;
     */
    public boolean updateMathComp(String uiType, TextView editText){
        if(uiType.equalsIgnoreCase("MathematicalRule") || uiType.equalsIgnoreCase("Query Rule")) {
            String eType = editText.getHint().toString();
            String s = editText.getText().toString();

            //if current editText is on mathematical computation's name
            if ("MathematicalRule".contentEquals(eType)) {
                //get currently changing mathematicalComputation object
                MathematicalComputation mathematicalComputation = getCurrentMathComp(uiType, editText);
                if(mathematicalComputation != null) {
                    //update mathematical computation object
                    if (mathematicalComputation.updateMathComp(s)) {
                        return true;
                    }
                }
                return false;
            }
            //current editText is in parameters of mathematical computation
            else {
                //get mathematical computation Id saved in Linear layout of full mathematical computation view in UI
                int parentId = ((View) editText.getParent().getParent()).getId();
                MathematicalComputation mathematicalComputation = getMathComp(parentId);
                //get mathematical computation
                if(mathematicalComputation != null) {
                    //get Id of mathematical computation parameter's parameter
                    int viewId = editText.getId();
                    //get Id of mathematical computation's parameter
                    int opId = ((View) editText.getParent()).getId();
                    //update mathematical computation object
                    if (mathematicalComputation.updateMathComp(s, eType, viewId, opId)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * @description Determine which mathematical computation object is currently selected ( query or element in coding playground ) and get it.
     * @param uiType
     * @param editText
     * @return MathematicalComputation object
     */
    private MathematicalComputation getCurrentMathComp(String uiType, TextView editText){
        View outerParent = (View) editText.getParent().getParent();
        //check if current editText is in coding playground
        if (outerParent.getId() == R.id.playground || outerParent.getId() == R.id.playgroundContainer) {
            int parentId = ((View) editText.getParent()).getId();
            return getMathComp(parentId);

        }
        //check if current editText is in consoleCommandLine
        else if (outerParent.getId() == R.id.consoleCommandLine) {
            return queryRule;
        }
        return null;
    }

    /**
     * @description Delete mathematical computation in mathComputation arraylist by given Id
     * @param id, integer
     */
    public void deleteMathComp(int id){
        Integer index = getMathCompIndex(id);
        if(index != null){
            MathematicalComputation temp = mathComputations.get(index);
            mathComputations.remove(temp);
        }
    }

    /**
     * @description Get index of MathematicalComputation in mathComputations arraylist given the Id
     * @param id, integer
     * @return Integer, index of mathematical computation object in mathComputations arraylist
     */
    private Integer getMathCompIndex(int id){
        for(int i = 0; i < mathComputations.size(); i++){
            MathematicalComputation temp = mathComputations.get(i);
            if(temp.getId() ==  id){
                return i;
            }
        }
        return null;
    }

    /**
     * @description check if all predicates in predicates arraylist is valid
     * @return boolean: True if all predicates are valid;
     */
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

    /**
     * @description Update query predicate in console command line
     * @param uiType, string
     * @param editText, TextView
     * @return
     */
    public boolean updateQuery(String uiType, TextView editText){
        if (uiType.equalsIgnoreCase("Query Predicate")) {
            CharSequence eType = editText.getHint();
            Editable s = editText.getEditableText();

            //update value for predicate's name
            if ("Predicate".contentEquals(eType)) {
                if(queryPredicate.updatePredicate(s)){
                    return true;
                }
                return false;
            }

            //update value for predicate's parameter
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

    /**
     * @description Update HashMap object used to save query rule variables
     * @param key, String
     * @param value, String
     * @return boolean: True if value updated
     */
    public boolean updateQueryRuleVariable(String key, String value){
        int index = 0;

        while(index < queryRuleVariables.size()){
            Map<String, String> m = queryRuleVariables.get(index);
            //if map object key matched
            if(m.containsKey(key)){
                //update value
                m.put(key, value);
                 return true;
            }
            index ++;
        }
        return false;
    }

    /**
     * @description Get HashMap object in queryRuleVariable arraylist given the key
     * @param key, String
     * @return Map object
     */
    private Map<String, String> getQueryRuleVariable(String key){
        for(Map<String, String> m : queryRuleVariables){
            if(m.containsKey(key)){
                return m;
            }
        }
        return null;
    }

    /**
     * @description Perform query search on given Predicate in predicate arraylist.
     * @param tempPred, Predicate
     * @return boolean: True if a match has been found
     */
    public boolean querySearch(Predicate tempPred){
        for(int i = 0; i < predicates.size(); i++){
            Predicate p = predicates.get(i);
            //check if p and given predicate, tempPred has the same number of parameters
            if(p.parametersArray.size() == tempPred.parametersArray.size()){
                //check if both predicates has the same name
                if(p.name.equalsIgnoreCase(tempPred.name)){
                    boolean match = true;
                    for(int j = 0; j < p.parametersArray.size(); j++){
                        Constant param1 = (Constant) p.parametersArray.get(j);
                        Constant param2 = (Constant) tempPred.parametersArray.get(j);
                        //matching parameter's value
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

    /**
     * @description Perform a variable search on given predicate in predicates arraylist
     * @param tempPred, Predicate object
     * @param state, integer: program state
     * @return String console log
     */
    public String variableSearch(Predicate tempPred, int state) {
        //Running State = 1;
        //VarSearch State = 3;

        //if interpreter is in running state, update interpreter to varSearch state
        // Set index back to 0. So, search can start from first element in predicates arraylist
        if(programState == 1){
            programState = 3;
            searchIndex = 0;
        }

        if (programState == 3) {
            //found indicate whether a match predicate has been found
            boolean found = false;
            String console = "";
            while (searchIndex < predicates.size() && !found) {
                boolean match = true;
                Predicate p = predicates.get(searchIndex);

                //compare whether both predicates has the same number of parameters
                if (tempPred.parametersArray.size() != p.parametersArray.size()) {
                    match = false;
                } else {
                    for (int i = 0; i < tempPred.parametersArray.size(); i++) {
                        Constant param1 = (Constant) p.parametersArray.get(i);
                        Constant param2 = (Constant) tempPred.parametersArray.get(i);

                        //check if input Predicate's parameter is a variable
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

            //if the every predicate in predicates arraylist has been searched
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

    /**
     * @description check if string s is a variable. String is a variable if the first character is in uppercase or '_'
     * @param s, String
     * @return boolean: True if s is string
     */
    private boolean isVariable(String s){

        char first = s.charAt(0);
        if(Character.isUpperCase(first) || first == '_'){
            return true;
        }
        return false;
    }

    /**
     * @description Perform interpretation on query on mathematical rule
     */
    public void interpretMathRule(){
        guiUpdater.hideView(R.id.interpret);

        //Running State = 1
        //InterpretRule State = 4

        //if interpreter is in running state, update interpreter to InterpretRule state
        // Set index back to 0. So, search can start from first element in mathComputations arraylist
        if(programState == 1){
            programState = 4;
            searchIndex = 0;
        }

        if(programState == 4) {
            MathematicalComputation curRule = getMathComp(queryRule.name);
            while (searchIndex < curRule.parametersArray.size() && queryRuleCondition) {
                //if parametersArray.get(searchIndex) is a Read object
                if (curRule.parametersArray.get(searchIndex) instanceof Read) {
                    Read r = (Read) curRule.parametersArray.get(searchIndex);
                    Map<String, String> m = new HashMap<>();
                    m.put(r.value, "");

                    //add new key value pair into hashmap arraylist, queryRuleVariables
                    queryRuleVariables.add(m);
                    String header = ((TextView) guiUpdater.getLastConsoleLog()).getText().toString();

                    //update value presented in UI
                    guiUpdater.updateReadInput(header, r.value);
                    guiUpdater.showView(R.id.input);
                    break;
                }
                //if parametersArray.get(searchIndex) is a Write object
                else if (curRule.parametersArray.get(searchIndex) instanceof Write) {
                    Write w = (Write) curRule.parametersArray.get(searchIndex);
                    String[] temp = w.value.split("");
                    //check if write value is a variable
                    if (!(temp.length > 1) && isVariable(w.value)) {
                        Map<String, String> m = getQueryRuleVariable(w.value);
                        guiUpdater.createConsoleLog(w.value + " = " + m.get(w.value));
                    } else {
                        guiUpdater.createConsoleLog(w.value);
                    }
                    searchIndex++;
                }
                //if parametersArray.get(searchIndex) is an Operator object
                else {
                    Operator op = (Operator) curRule.parametersArray.get(searchIndex);
                    //convert operator's parameters into a single string which form a math expression
                    String expression = op.convertOperatorToString(queryRuleVariables);

                    //EvalEx library is imported and used
                    Expression e = new Expression(expression);

                    //if evaluation on math expression returns 0, condition is false
                    if (e.eval().toString().equalsIgnoreCase("0")) {
                        queryRuleCondition = false;
                    }
                    searchIndex++;
                }
            }

            //when all parameters in mathComputation object is went through
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
