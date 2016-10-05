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
        else{
            mainInterpreter.stopInterpreter(programState);
        }
    }
}
