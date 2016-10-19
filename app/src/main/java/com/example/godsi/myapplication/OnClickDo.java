package com.example.godsi.myapplication;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is responsible for the response of the start, run, stop actions of the LittleProlog Interpreter
 * @author: Chan Kai Ying
 */

public class OnClickDo implements View.OnClickListener {

    private String consoleText; //text to be printed on console
    private GUIUpdater guiUpdater; //main GUIupdater of the activity
    private MainInterpreter mainInterpreter; //main interpreter of the activity
    private String type; //ui type
    private String failConsoleText; //text to be printed on console if action fails
    private int programState; //state of the interpreter
    private int failState; //failure state

    /**
     * Constructor for OnClickDo class
     * @param console
     * @param gui
     * @param interpreter
     * @param uiType
     * @param failText
     * @param programStateId
     * @param failStateId
     */
    public OnClickDo(String console, GUIUpdater gui, MainInterpreter interpreter, String uiType, String failText, int programStateId, int failStateId){
        consoleText = console;
        guiUpdater = gui;
        mainInterpreter = interpreter;
        type = uiType;
        failConsoleText = failText;
        programState = programStateId;
        failState = failStateId;
    }

    /***
     * Logs messages based on the ui type and updates program state
     * @param v View clicked
     */
    @Override
    public void onClick(View v) {
        //when "play" button is clicked, run interpreter
        if(type.equalsIgnoreCase("Run")){
            //check if all elements in coding playground are valid
            if(mainInterpreter.runInterpreter(programState)){
                guiUpdater.createConsoleLog(consoleText);

            }
            //if coding playground has invalid elements, fail to start interpreter
            else{
                mainInterpreter.stopInterpreter(failState);
                guiUpdater.createConsoleLog(failConsoleText);
            }
        }
        //when "Stop" button is clicked, stop interpreter
        else if(type.equalsIgnoreCase("Stop")){
            mainInterpreter.stopInterpreter(programState);
            guiUpdater.createConsoleLog(consoleText);
        }

        //interpret queryPredicate entered
        //if 'send' button is clicked
        else if(type.equalsIgnoreCase("Enter")){
            Predicate query = mainInterpreter.queryPredicate;
            //program is currently in edit state
            if(mainInterpreter.programState == 1){
                //check whether input needs querySearch or variableSearch
                int search = query.queryOrVariableSearch();
                guiUpdater.createConsoleLog(consoleLogQueryPredicate(query));
                //querySearch
                if(search == 1){
                    //set program current state to querySearch state
                    mainInterpreter.programState = 2;
                    if(mainInterpreter.querySearch(query)){
                        guiUpdater.createConsoleLog("Yes.");
                    }
                    else{
                        guiUpdater.createConsoleLog("No.");
                    }
                    //after finish interpreting queryPredicate, switch program state back to running state
                    mainInterpreter.programState = 1;
                }

                //variableSearch
                else if(search == 2){
                    String result = "";
                    if(mainInterpreter.programState == 1){
                        result += mainInterpreter.variableSearch(query, 1);
                    }
                    if(result != null){
                        guiUpdater.createConsoleLog(result);
                    }
                }
            }
            else if(mainInterpreter.programState == 3){
                //variableSearch with "enter" button
                String result = mainInterpreter.variableSearch(query, 2);
                if(result != null){
                    guiUpdater.createConsoleLog(result);
                }
            }
        }

        //when ";" button is clicked
        //continue variable search
        else if(type.equalsIgnoreCase("Next")){
            Predicate query = mainInterpreter.queryPredicate;
            String result = mainInterpreter.variableSearch(query, 1);
            if(result != null){
                guiUpdater.createConsoleLog(result);
            }
        }

        //When "Interpret" button in console command line is clicked, start interpreting mathematical rule query
        else if(type.equalsIgnoreCase("Interpret")){
            MathematicalComputation query = mainInterpreter.queryRule;

            //create console log to print the full mathematical rule
            guiUpdater.createConsoleLog(consoleLogQueryRule(query.name));

            //remove mathematical rule query from console command line
            removeQueryRuleView();

            //start interpretation only if program is in running state or interpret query rule state
            if(mainInterpreter.programState == 1 || mainInterpreter.programState == 4){
                //interpret query
                mainInterpreter.interpretMathRule();
            }
            //fail to start interpretation due to program not in the correct state
            else{
                guiUpdater.createConsoleLog("Interpreter has not been started.");
            }
        }

        //When "submit" button in console command line is clicked,
        // Accept user input for Read functionality. Update console log to print user input value and clear input TextView in console command line
        else if(type.equalsIgnoreCase("Read Input")){

            //get input TextView in console command line
            LinearLayout readInput = (LinearLayout) guiUpdater.getView(R.id.readInput);
            TextView input = (TextView) readInput.getChildAt(1);

            //Update HashMap object's value saved in backend
            mainInterpreter.updateQueryRuleVariable(input.getHint().toString(), input.getText().toString());

            //Update console log to print user input value
            TextView log = (TextView) guiUpdater.getLastConsoleLog();
            String logValue = log.getText().toString();
            log.setText(logValue + " " + input.getText() + ".");

            //clear input TextView in console command line
            input.setText("");
            guiUpdater.hideView(R.id.input);
            guiUpdater.hideView(R.id.readInput);

            //continue interpreting query mathematical rule
            mainInterpreter.searchIndex ++;
            mainInterpreter.interpretMathRule();
        }
    }

    private void removeQueryRuleView(){
        int queryRuleId = mainInterpreter.queryRule.getId();
        guiUpdater.removeView(queryRuleId);
    }

    /**
     * Create a string that consists of the full predicate matching the query predicate in the format of,
     * for example Predicate(X, Y).
     * @param query, Predicate object
     * @return String
     */
    private String consoleLogQueryPredicate(Predicate query){
        String log = query.name + "(";
        for(int i = 0; i < query.parametersArray.size(); i++){
            Constant c = (Constant) query.parametersArray.get(i);
            log += c.value + ", ";
        }
        log = log.substring(0, log.length() - 2);
        log += ").";

        return log;
    }

    /**
     * Create a string that consists of the full mathematical rule matching the query mathematical rule in the format of,
     * for example Rule :- Write("input height: "), Read( X ), X == 3 + 4.
     * @param name, mathematical rule name
     * @return String
     */
    private String consoleLogQueryRule(String name){
        MathematicalComputation query = mainInterpreter.getMathComp(name);
        String log = query.name + " :- ";
        for(int i = 0; i < query.parametersArray.size(); i++){

            //if parameter is a Read object
            if(query.parametersArray.get(i) instanceof Read){
                log += "Read ( ";
                Read p = (Read) query.parametersArray.get(i);
                log += p.value;
                log += " ), ";
            }

            //if parameter is a Write object
            else if(query.parametersArray.get(i) instanceof Write){
                log += "Write ( ";
                Write p = (Write) query.parametersArray.get(i);
                log += p.value;
                log += " ), ";
            }

            //if parameter is an Operator object
            else{
                Operator p = (Operator) query.parametersArray.get(i);
                ArrayList<Attribute> opParams = p.parametersArray;
                Constant c;
                OperatorType opType;
                for(int j = 0; j < opParams.size(); j++){
                    //if parameter is a Constant object
                    if(opParams.get(j) instanceof Constant) {
                        c = (Constant) opParams.get(j);
                        log += c.value + " ";
                    }

                    //if parameter is a Operator Type object
                    else{
                        opType = (OperatorType) opParams.get(j);
                        log += opType.value + " ";
                    }
                }
                log += ", ";
            }
        }

        //remove unnecessary ", " at the end of log string and add "." at the end
        log = log.substring(0, log.length() - 2);
        log += ".";
        return log;
    }
}
