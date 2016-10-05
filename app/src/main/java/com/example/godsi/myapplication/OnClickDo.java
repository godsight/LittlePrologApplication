package com.example.godsi.myapplication;

import android.view.View;

/**
 * @author: Chan Kai Ying
 */

public class OnClickDo implements View.OnClickListener {

    private String consoleText;
    private GUIUpdater guiUpdater;
    private MainInterpreter mainInterpreter;
    private String type;
    private String failConsoleText;
    private int programState;
    private int failState;

    public OnClickDo(String console, GUIUpdater gui, MainInterpreter interpreter, String uiType, String failText, int programStateId, int failStateId){
        consoleText = console;
        guiUpdater = gui;
        mainInterpreter = interpreter;
        type = uiType;
        failConsoleText = failText;
        programState = programStateId;
        failState = failStateId;
    }

    @Override
    public void onClick(View v) {
        if(type.equalsIgnoreCase("Run")){
            if(mainInterpreter.runInterpreter(programState)){
                guiUpdater.createConsoleLog(consoleText);

            }
            else{
                mainInterpreter.stopInterpreter(failState);
                guiUpdater.createConsoleLog(failConsoleText);
            }
        }
        else if(type.equalsIgnoreCase("Stop")){
            mainInterpreter.stopInterpreter(programState);
        }

        else if(type.equalsIgnoreCase("Enter")){
            Predicate query = mainInterpreter.query;
            if(mainInterpreter.programState == 1){
                int search = query.queryOrVariableSearch();
                if(search == 1){
                    mainInterpreter.programState = 2;
                    if(mainInterpreter.querySearch(query)){
                        guiUpdater.createConsoleLog("Yes.");
                    }
                    else{
                        guiUpdater.createConsoleLog("No.");
                    }
                    mainInterpreter.programState = 1;
                }
                else if(search == 2){
                    mainInterpreter.programState = 3;
                    String result = mainInterpreter.variableSearch(query, 2);
                    if(result != null){
                        guiUpdater.createConsoleLog(result);
                    }
                }
            }
            else if(mainInterpreter.programState == 3){
                mainInterpreter.programState = 3;
                String result = mainInterpreter.variableSearch(query, 2);
                if(result != null){
                    guiUpdater.createConsoleLog(result);
                }
            }
        }

        else if(type.equalsIgnoreCase("Next")){
            Predicate query = mainInterpreter.query;
            if(mainInterpreter.programState == 3){
                String result = mainInterpreter.variableSearch(query, 1);
                if(result != null){
                    guiUpdater.createConsoleLog(result);
                }
            }
        }
    }
}
