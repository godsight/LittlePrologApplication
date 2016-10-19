package com.example.godsi.myapplication;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class is a listener which listens for focus change events and handles it
 * @author Hong Chin Choong & Chan Kai Ying
 * @version 0.1v
 */
public class FocusListener implements View.OnFocusChangeListener {

    private String consoleText; //text used when creating console log for the change
    private String instruction; //instruction to be shown on instruction view
    private String uiType; //element type
    private GUIUpdater guiUpdater; //the GUI handler of the activity
    private MainInterpreter mainInterpreter; //main class for backend operations

    /**
     * Constuctor for FocusListener class
     * @param console
     * @param instructionText
     * @param type
     * @param updater
     * @param interpreter
     */
    public FocusListener(String console, String instructionText, String type, GUIUpdater updater, MainInterpreter interpreter){
        consoleText = console;
        instruction = instructionText;
        uiType = type;
        guiUpdater = updater;
        mainInterpreter = interpreter;
    }

    /**
     * Updates the instruction screen when the view is being focused && Updates elements' value in backend and create console log for the changes made
     * after losing focus
     * @param view The view which is focused/unfocused
     * @param b focus state, true if focused else false
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        //In Focus
        if (b){
            guiUpdater.updateInstructions(instruction);
        }

        //Lose focus
        else {
            //for predicate elements in coding playground
            if(uiType.equalsIgnoreCase("Predicate")) {
                if(mainInterpreter.updatePredicate(uiType, (TextView)view)){
                    guiUpdater.createConsoleLog(consoleText + " " + ((TextView) view).getText().toString());
                }
            }

            //for query predicate elements in console command line
            else if(uiType.equalsIgnoreCase("Query")){
                if(mainInterpreter.updateQuery(uiType, (TextView)view)){
                    guiUpdater.createConsoleLog(consoleText + " " +  ((TextView) view).getText().toString());
                }
            }

            //for mathematical rule elements in coding playground
            else if(uiType.equalsIgnoreCase("MathematicalRule")){
                if(mainInterpreter.updateMathComp(uiType, (TextView) view)){
                    guiUpdater.createConsoleLog(consoleText + " " + ((TextView) view).getText().toString());
                }
            }
        }
    }

}
