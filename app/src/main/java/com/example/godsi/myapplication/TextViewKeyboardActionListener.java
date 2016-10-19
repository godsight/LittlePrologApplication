package com.example.godsi.myapplication;

import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This class is responsible to respond after "Done" key in keyboard is clicked when editing a TextView
 * @author: Chan Kai Ying
 */

public class TextViewKeyboardActionListener implements TextView.OnEditorActionListener {

    private String consoleText; //string for creating console log
    private String uiType; //element type
    private GUIUpdater guiUpdater; //the GUI handler of the activity
    private MainInterpreter mainInterpreter; //main class for backend operations

    public TextViewKeyboardActionListener(String console, String type, GUIUpdater gui, MainInterpreter interpreter){
        consoleText = console;
        uiType = type;
        guiUpdater = gui;
        mainInterpreter = interpreter;
    }

    /**
     * Currently, this method detects "Done" key clicked action and update backend elements correspond to the UI views changed
     * @param view, TextView focus
     * @param actionId, Id for action
     * @param event, keyboard key clicked event
     * @return boolean
     */
    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event){
        if(actionId == EditorInfo.IME_ACTION_DONE){

            //for predicate element in coding playground
            if(uiType.equalsIgnoreCase("Predicate")) {
                if(mainInterpreter.updatePredicate(uiType, view)){
                    guiUpdater.createConsoleLog(consoleText + " " + view.getText().toString());
                }
            }

            //for predicate element in console command line
            else if(uiType.equalsIgnoreCase("Query Predicate")){
                if(mainInterpreter.updateQuery(uiType, view)){
                    guiUpdater.createConsoleLog(consoleText + " " + view.getText().toString());
                }
            }

            //for mathematical computation element in both coding playground and console command line
            else if(uiType.equalsIgnoreCase("MathematicalRule") || uiType.equalsIgnoreCase("Query Rule")){
                if(mainInterpreter.updateMathComp(uiType, view)){
                    guiUpdater.createConsoleLog(consoleText + " " + view.getText().toString());
                }
            }

            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

}
