package com.example.godsi.myapplication;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class is a listener which listens for focus change events and handles it
 * @author Hong Chin Choong
 * @version 0.1v
 */
public class FocusListener implements View.OnFocusChangeListener {

    private String consoleText;
    private String instruction; //instruction to be shown on instruction view
    private String uiType;
    private GUIUpdater guiUpdater; //the GUI handler of the activity
    private MainInterpreter mainInterpreter;
    private String curText;

    public FocusListener(String console, String instructionText, String type, GUIUpdater updater, MainInterpreter interpreter){
        consoleText = console;
        instruction = instructionText;
        uiType = type;
        guiUpdater = updater;
        mainInterpreter = interpreter;
    }

    /**
     * Updates the instruction screen when the view is being focused
     * @param view The view which is focused/unfocused
     * @param b focus state, true if focused else false
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            guiUpdater.updateInstructions(instruction);
            curText = ((TextView)view).getText().toString();
        }
        else if(!curText.equalsIgnoreCase(((TextView)view).getText().toString()) && !b){
            if(uiType.equalsIgnoreCase("Predicate")) {
                if(mainInterpreter.updatePredicate(uiType, (TextView)view)){
                    guiUpdater.createConsoleLog(consoleText + " " + ((TextView) view).getText().toString());
                }
            }
            else if(uiType.equalsIgnoreCase("Query")){
                if(mainInterpreter.updateQuery(uiType, (TextView)view)){
                    guiUpdater.createConsoleLog(consoleText + " " +  ((TextView) view).getText().toString());
                }
            }
            view.clearFocus();
        }
    }

}
