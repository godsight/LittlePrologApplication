package com.example.godsi.myapplication;

import android.view.View;

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
        //run interpreter
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
        //stop interpreter
        else if(type.equalsIgnoreCase("Stop")){
            mainInterpreter.stopInterpreter(programState);
        }

        //interpret query entered
        //if 'send' button is clicked
        else if(type.equalsIgnoreCase("Enter")){
            Predicate query = mainInterpreter.query;
            //program is currently in edit state
            if(mainInterpreter.programState == 1){
                //check whether input needs querySearch or variableSearch
                int search = query.queryOrVariableSearch();

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
                    //after finish interpreting query, switch program state back to running state
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

        else if(type.equalsIgnoreCase("Next")){
            Predicate query = mainInterpreter.query;
            String result = mainInterpreter.variableSearch(query, 1);
            if(result != null){
                guiUpdater.createConsoleLog(result);
            }
        }
    }
}
